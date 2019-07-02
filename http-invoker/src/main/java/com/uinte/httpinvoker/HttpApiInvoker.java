package com.uinte.httpinvoker;

import static com.uinte.httpinvoker.util.ParamUtils.isCollection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.uinte.httpinvoker.annotation.ContentType;
import com.uinte.httpinvoker.annotation.Cookies;
import com.uinte.httpinvoker.annotation.Form;
import com.uinte.httpinvoker.annotation.Headers;
import com.uinte.httpinvoker.annotation.HttpApi;
import com.uinte.httpinvoker.annotation.HttpReq;
import com.uinte.httpinvoker.annotation.Param;
import com.uinte.httpinvoker.annotation.RetryPolicy;
import com.uinte.httpinvoker.annotation.UserAgent;
import com.uinte.httpinvoker.propertyresolver.PropertiesBasePropertyResolver;
import com.uinte.httpinvoker.propertyresolver.PropertyResolver;
import com.uinte.httpinvoker.requestor.DefaultHttpRequestor;
import com.uinte.httpinvoker.requestor.DefaultResponseProcessor;
import com.uinte.httpinvoker.requestor.HttpRequest;
import com.uinte.httpinvoker.requestor.HttpResponse;
import com.uinte.httpinvoker.requestor.MultiPart;
import com.uinte.httpinvoker.requestor.RequestPreprocessor;
import com.uinte.httpinvoker.requestor.Requestor;
import com.uinte.httpinvoker.requestor.ResponseProcessor;
import com.uinte.httpinvoker.requestor.Status;
import com.uinte.httpinvoker.util.ParamUtils;

/**
 * an InvocationHandler which request the url that the annotation's value attribute specify when the annotated method is invoked.
 * <p>
 * And then parse the response body to the return value(if it has) using FastJson, generic type is also supported.
 *
 * @author huangxuyang
 * date 2018/11/27
 */
public class HttpApiInvoker implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpApiInvoker.class);
    private static final ResponseProcessor DEFAULT_RESPONSE_PROCESSOR = new DefaultResponseProcessor();
    private static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("\\{([^/]+?)}");
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^/]+?)}");
    private static final Pattern PATH_NOT_REMOVE_VARIABLE_PATTERN = Pattern.compile("#\\{([^/]+?)}");
    private static final Pattern PROTOCOL_PATTERN = Pattern.compile("^[a-zA-Z].+://");
    private static final int OK_CODE_L = 200;
    private static final int OK_CODE_H = 300;
    private static final String HTTP_API_PREFIX = "$HttpApi$";
    private static final String TO_STRING = "toString";
    private static final String CONTENT_TYPE = "Content-Type";
    private Requestor requestor;
    private PropertyResolver propertyResolver;
    private Class<?> clazz;
    private RequestPreprocessor requestPreprocessor;
    private ResponseProcessor responseProcessor;

    public HttpApiInvoker(Requestor requestor, Properties properties,
                          Class<?> clazz, RequestPreprocessor requestPreprocessor,
                          ResponseProcessor responseProcessor) {
        this.requestor = requestor == null ? new DefaultHttpRequestor() : requestor;
        properties = properties == null ? System.getProperties() : properties;
        this.propertyResolver = new PropertiesBasePropertyResolver(properties);
        this.requestPreprocessor = requestPreprocessor;
        this.responseProcessor = responseProcessor;
        this.clazz = clazz;
    }

    public HttpApiInvoker(Requestor requestor, PropertyResolver propertyResolver,
                          Class<?> clazz, RequestPreprocessor requestPreprocessor,
                          ResponseProcessor responseProcessor) {
        this.requestor = requestor == null ? new DefaultHttpRequestor() : requestor;
        this.propertyResolver = propertyResolver == null ? new PropertiesBasePropertyResolver(System.getProperties()) : propertyResolver;
        this.requestPreprocessor = requestPreprocessor;
        this.responseProcessor = responseProcessor;
        this.clazz = clazz;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(HttpReq.class)) {
            // toString method with a specific prefix
            if (TO_STRING.equals(method.getName()) && method.getParameterTypes().length == 0) {
                return HTTP_API_PREFIX + this;
            }
            // those Object's methods invoke 'this' method
            if (this.getClass().getMethod(method.getName(), method.getParameterTypes()) != null) {
                return method.invoke(this, args);
            }
            // this proxy only implement those HttpReq-annotated method
            throw new IllegalStateException("this proxy only implement those HttpReq-annotated method");
        }
        HttpReq anno = method.getAnnotation(HttpReq.class);
        String url = prepareUrl(anno);
        HttpRequest request = new HttpRequest(anno.timeout(), anno.method());
        request.setUrl(url);
        if (args != null && args.length > 0) {
            // find MultiPart
            for (Object arg : args) {
                if (arg instanceof MultiPart) {
                    if (!"POST".equalsIgnoreCase(request.getMethod())) {
                        throw new IllegalStateException("unsupported request method for multipart form: " + request.getMethod());
                    }
                    request.setBody(arg);
                }
            }
            Map<String, Object> params = null;
            Map<String, Object> annotatedParam = parseAnnotatedParams(args, method, request);
            // use annotated param if exists
            if (annotatedParam != null && !annotatedParam.isEmpty()) {
                params = annotatedParam;
            } else if (request.getBody() == null) {
                // else use the first arg as param
                if (isCollection(args[0])) {
                    request.setBody(args[0]);
                } else if (args[0] != null) {
                    params = parseParam(args[0]);
                }
            } else {
                // try the parse body to a map
                request.setData(parseParam(request.getBody()));
            }
            // fill path variable for the url
            url = fillPathVariables(params, url, false);
            request.setUrl(url);
            request.setData(params);
        }
        addHeaders(method, request);
        addCookies(method, request);
        addContentType(method, request);
        addUserAgent(method, request);

        if (requestPreprocessor != null) {
            requestPreprocessor.process(request);
        }
        // fill path variable again, so that user can provide some param by requestPreprocessor
        url = fillPathVariables(request.getData(), url, true);
        request.setUrl(url);
        long start = System.currentTimeMillis();
        HttpResponse response;
        RetryPolicy retryPolicy = getRetryPolicy(method);
        if (retryPolicy == null) {
            response = requestor.sendRequest(request);
        } else {
            response = retrySendRequest(request, retryPolicy);
        }
        if (isSuc(url, response)) {
            return null;
        }
        Object returnValue;
        if (responseProcessor != null) {
            returnValue = responseProcessor.process(response, method);
        } else {
            returnValue = DEFAULT_RESPONSE_PROCESSOR.process(response, method);
        }
        if (log.isDebugEnabled()) {
            log.debug("send request to url: {}, time consume: {} ms", request.getUrl(), (System.currentTimeMillis() - start));
        }
        return returnValue;
    }

    private void addContentType(Method method, HttpRequest request) {
        if (clazz.isAnnotationPresent(Form.class)
                || method.isAnnotationPresent(Form.class)) {
            // a form request
            request.addHeader(CONTENT_TYPE, "application/x-www-form-urlencoded");
            return;
        }
        ContentType contentType = getAnn(method, ContentType.class);
        if (contentType != null && !contentType.value().isEmpty()) {
            request.addHeader(CONTENT_TYPE, contentType.value());
        }
    }

    private void addUserAgent(Method method, HttpRequest request) {
        UserAgent userAgent = getAnn(method, UserAgent.class);
        if (userAgent != null && !userAgent.value().isEmpty()) {
            request.addHeader("User-Agent", userAgent.value());
        }
    }

    private void addCookies(Method method, HttpRequest request) {
        if (method.getDeclaringClass().isAnnotationPresent(Cookies.class)) {
            Cookies headers = method.getDeclaringClass().getAnnotation(Cookies.class);
            addCookies(request, headers);
        }
        // method's cookies will cover those with same key
        if (method.isAnnotationPresent(Cookies.class)) {
            Cookies headers = method.getAnnotation(Cookies.class);
            addCookies(request, headers);
        }
    }

    private void addCookies(HttpRequest request, Cookies cookies) {
        if (cookies != null && cookies.keys().length > 0) {
            if (cookies.values().length == cookies.keys().length) {
                for (int i = 0; i < cookies.keys().length; i++) {
                    String key = cookies.keys()[i];
                    String value = cookies.values()[i];
                    request.addCookie(key, value);
                }
            } else {
                throw new IllegalStateException("cookies' keys and values must one-to-one correspondence");
            }
        }
    }

    private void addHeaders(Method method, HttpRequest request) {
        if (method.getDeclaringClass().isAnnotationPresent(Headers.class)) {
            Headers headers = method.getDeclaringClass().getAnnotation(Headers.class);
            addHeaders(request, headers);
        }
        // method's headers will cover those with same key
        if (method.isAnnotationPresent(Headers.class)) {
            Headers headers = method.getAnnotation(Headers.class);
            addHeaders(request, headers);
        }
    }

    private void addHeaders(HttpRequest request, Headers headers) {
        if (headers != null && headers.keys().length > 0) {
            if (headers.values().length == headers.keys().length) {
                for (int i = 0; i < headers.keys().length; i++) {
                    String key = headers.keys()[i];
                    String value = headers.values()[i];
                    request.addHeader(key, value);
                }
            } else {
                throw new IllegalStateException("headers' keys and values must one-to-one correspondence");
            }
        }
    }

    private <T extends Annotation> T getAnn(Method method, Class<T> ann) {
        if (method.isAnnotationPresent(ann)) {
            return method.getAnnotation(ann);
        } else if (method.getDeclaringClass().isAnnotationPresent(ann)) {
            return method.getDeclaringClass().getAnnotation(ann);
        }
        return null;
    }

    private String prepareUrl(HttpReq anno) {
        String url = anno.value();
        // fill config variables
        url = fillConfigVariables(url);
        url = setPrefix(url);
        // fill config variables again, because the prefix may have placeholders
        return fillConfigVariables(url);
    }

    private String setPrefix(String url) {
        // if the interface was annotated by @HttpApi and the url has no protocol, set the prefix
        if (clazz.isAnnotationPresent(HttpApi.class)
                && !PROTOCOL_PATTERN.matcher(url).find()) {
            HttpApi httpApi = clazz.getAnnotation(HttpApi.class);
            // use prefix or value of HttpApi as the url's prefix
            String pre = "".equals(httpApi.prefix()) ? httpApi.value() : httpApi.prefix();
            url = pre + url;
        }
        return url;
    }

    private boolean isSuc(String url, HttpResponse response) throws IOException {
        if (response == null) {
            return true;
        }
        if (response.getStatusCode() < OK_CODE_L || response.getStatusCode() >= OK_CODE_H) {
            // status code is not 2xx
            throw new IOException(url + ", statusCode: " + response.getStatusCode() + ", statusMsg: " + response.getStatusMessage());
        }
        return false;
    }

    /**
     * retry send request according to the retry policy
     */
    private HttpResponse retrySendRequest(HttpRequest request, RetryPolicy retryPolicy) throws IOException {
        int retryTime = retryPolicy.times();
        if (retryTime <= 0) {
            return requestor.sendRequest(request);
        }
        Status[] retryForStatus = retryPolicy.retryForStatus();
        Class<? extends Throwable>[] retryFor = retryPolicy.retryFor();
        HttpResponse response = null;
        int tryTime = 0;
        while (++tryTime <= retryTime) {
            if (tryTime > 1 && retryPolicy.fixedBackOffPeriod() > 0) {
                try {
                    Thread.sleep(retryPolicy.fixedBackOffPeriod());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("thread interrupted when waiting for retry", e);
                }
            }
            boolean needRetry = false;
            try {
                response = requestor.sendRequest(request);
                int statusCode = response.getStatusCode();
                for (Status status : retryForStatus) {
                    if (statusCode >= status.getFrom() && statusCode <= status.getTo()) {
                        needRetry = true;
                    }
                }
                if (!needRetry) {
                    return response;
                }
            } catch (IOException e) {
                if (tryTime >= retryTime) {
                    // it's the last time we try
                    throw e;
                }
                // check if we need to retry
                for (Class<? extends Throwable> exception : retryFor) {
                    if (exception.isAssignableFrom(e.getClass())) {
                        needRetry = true;
                    }
                }
                if (!needRetry) {
                    // an unexpected exception occur, so we throw it
                    throw e;
                } else {
                    log.warn("send request error, tryTime: {}, url: {}, error: {}", tryTime, request.getUrl(), e.getMessage());
                }
            }
        }
        return response;
    }

    private RetryPolicy getRetryPolicy(Method method) {
        RetryPolicy retryPolicy = null;
        if (method.isAnnotationPresent(RetryPolicy.class)) {
            retryPolicy = method.getAnnotation(RetryPolicy.class);
        } else if (clazz.isAnnotationPresent(RetryPolicy.class)) {
            retryPolicy = clazz.getAnnotation(RetryPolicy.class);
        }
        return retryPolicy;
    }

    private Map<String, Object> parseParam(Object arg) {
        Map<String, Object> params;
        Class<?> cls = arg.getClass();
        if (ParamUtils.isBasicType(cls)
                || isCollection(arg)) {
            // we don't handle collection param here
            params = null;
        } else {
            params = JSON.parseObject(JSON.toJSONString(arg));
        }
        return params;
    }


    /**
     * parse arguments annotated by @Param annotation to a map
     * <p>
     * the annotation's value stand for key and the argument represent value
     * <p>
     *
     * @param args    the arguments
     * @param method  the method invoked
     * @param request the request
     * @return the map represent the params
     */
    private Map<String, Object> parseAnnotatedParams(Object[] args, Method method, HttpRequest request) {
        Annotation[][] annotations = method.getParameterAnnotations();
        if (annotations.length <= 0) {
            return Collections.emptyMap();
        }
        Map<String, Object> map = null;
        for (int i = 0, annotationsLength = annotations.length; i < annotationsLength; i++) {
            if (args[i] == null) {
                // ignore null value
                continue;
            }
            Annotation[] annotation = annotations[i];
            if (annotation.length == 0) {
                continue;
            }
            for (Annotation ann : annotation) {
                if (ann instanceof Param) {
                    Param param = (Param) ann;
                    if (map == null) {
                        map = new HashMap<String, Object>();
                    }
                    if (isFile(args[i])) {
                        request.setBody(args[i]);
                        request.setFileFormKey(param.value());
                    } else if (param.isBody()) {
                        Map<String, Object> body = parseParam(args[i]);
                        if (body == null) {
                            map.put(param.value(), args[i]);
                        } else {
                            map.putAll(body);
                        }
                    } else {
                        String key = param.value();
                        if (!key.isEmpty()) {
                            map.put(key, args[i]);
                        }
                    }
                    // ignore when the param annotation's value is empty and isBody is false
                }
                if (ann instanceof Headers) {
                    mustBeMapStringString(method.getGenericParameterTypes()[i]);
                    //noinspection unchecked
                    request.setHeaders((Map<String, String>) args[i]);
                }
                if (ann instanceof Cookies) {
                    mustBeMapStringString(method.getGenericParameterTypes()[i]);
                    //noinspection unchecked
                    request.setCookies((Map<String, String>) args[i]);
                }
            }
        }
        return map;
    }

    private boolean isFile(Object arg) {
        return InputStream.class.isAssignableFrom(arg.getClass())
                || File.class.isAssignableFrom(arg.getClass());
    }

    /**
     * Check whether the Type is Map&lt;String, String&gt;
     */
    private void mustBeMapStringString(Type arg) {
        if (!(arg instanceof ParameterizedType) || ((ParameterizedType) arg).getRawType() != Map.class) {
            throw new IllegalArgumentException("Headers and Cookies annotation should only be annotated on parameter of Map<String, String> type.");
        }
        Type[] types = ((ParameterizedType) arg).getActualTypeArguments();
        if (types[0] != String.class || types[1] != String.class) {
            throw new IllegalArgumentException("Headers and Cookies annotation should only be annotated on parameter of Map<String, String> type.");
        }
    }

    /**
     * replace the path variable for the specific param, and remove that param from the map
     *
     * @param exceptionOnNotProvided if throw an exception on path variable doesn't provided
     * @return the path variable filled url
     * @throws IllegalArgumentException thrown when the specific param absent
     */
    private String fillPathVariables(Map<String, Object> params, String url, boolean exceptionOnNotProvided) {
        url = fillPathVariables(params, url, exceptionOnNotProvided, false);
        return fillPathVariables(params, url, exceptionOnNotProvided, true);
    }

    /**
     * replace the path variable for the specific param, and remove that param from the map
     *
     * @param exceptionOnNotProvided if throw an exception on path variable doesn't provided
     * @param remove                 remove the param if matches the path variable
     * @return the path variable filled url
     * @throws IllegalArgumentException thrown when the specific param absent
     */
    private String fillPathVariables(Map<String, Object> params, String url, boolean exceptionOnNotProvided, boolean remove) {
        Pattern pattern = remove ? PATH_VARIABLE_PATTERN : PATH_NOT_REMOVE_VARIABLE_PATTERN;
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            String key = matcher.group(1);
            if (params == null
                    || !params.containsKey(key)
                    || params.get(key) == null) {
                // path variable must be provided
                String msg = "the url [" + url + "] needs a variable: [" + key + "], but not provided.";
                log.warn(msg);
                if (exceptionOnNotProvided) {
                    throw new IllegalArgumentException(msg);
                } else {
                    continue;
                }
            }
            String prop;
            if (remove) {
                prop = params.remove(key).toString();
                url = url.replace("{" + key + "}", prop);
            } else {
                prop = params.get(key).toString();
                url = url.replace("#{" + key + "}", prop);
            }
        }
        return url;
    }

    private String fillConfigVariables(String url) {
        Matcher matcher = VARIABLE_PATTERN.matcher(url);
        while (matcher.find()) {
            String key = matcher.group(1);
            if (propertyResolver == null || !propertyResolver.containsProperty(key)) {
                // path variable must be provided
                String msg = "the url [" + url + "] needs a variable: [" + key + "], but not provided.";
                log.warn(msg);
                throw new IllegalArgumentException(msg);
            }
            String prop = propertyResolver.getProperty(key);
            url = url.replace("${" + key + "}", prop);
        }
        return url;
    }

}
