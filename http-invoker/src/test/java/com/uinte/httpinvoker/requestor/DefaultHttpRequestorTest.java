package com.uinte.httpinvoker.requestor;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.uinte.httpinvoker.annotation.HttpReq;
import com.uinte.httpinvoker.interfaces.CityService;
import com.uinte.httpinvoker.requestor.DefaultHttpRequestor;
import com.uinte.httpinvoker.requestor.HttpRequest;
import com.uinte.httpinvoker.requestor.HttpResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class DefaultHttpRequestorTest {
    private DefaultHttpRequestor defaultHttpRequestor;
    private static final int PORT = 18888;
    @Rule
    public WireMockRule cityIoService = new WireMockRule(options().port(PORT));

    @Before
    public void setUp() throws Exception {
        defaultHttpRequestor = new DefaultHttpRequestor();
    }

    @Test
    public void sendRequest() throws Exception {
        String url = "http://localhost:" + PORT + "/getAllCities";
        HttpReq anno = CityService.class.getMethod("getAllCities").getAnnotation(HttpReq.class);
        cityIoService.stubFor(get(urlEqualTo("/getAllCities")).willReturn(aResponse().withBody("abc")));
        HttpRequest request = new HttpRequest(url);
        HttpResponse response = defaultHttpRequestor.sendRequest(request);
        Assert.assertEquals("abc", response.getBody());
    }
}