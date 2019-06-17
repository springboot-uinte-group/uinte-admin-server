package com.uinte.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class BaseEntityTools {

	/**
	 * 将实体转换成Map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object bean) {
		Class<?> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String tempPropertyName = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				tempPropertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					if (readMethod != null) {
						Object result = null;
						try {
							result = readMethod.invoke(bean, new Object[0]);
						} catch (Exception e) {
							continue;
						}
						if (result != null) {
							returnMap.put(propertyName, result);
						} else {
							returnMap.put(propertyName, result);
						}
					}
				}
			}
		} catch (Exception e) {
			returnMap.put(tempPropertyName, e.getMessage());
		}
		return returnMap;
	}

	/**
	 * 将实体转换成Json
	 * 
	 * @param bean
	 * @return
	 */
	public static JSONObject beanToJson(Object bean) {
		Class<?> type = bean.getClass();
		JSONObject json = new JSONObject();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					if (readMethod != null) {
						Object result = readMethod.invoke(bean, new Object[0]);
						if (result != null) {
							json.put(getFieldAnnotation(type, propertyName), result);
						} else {
							json.put(getFieldAnnotation(type, propertyName), result);
						}
					}
				}
			}
		} catch (Exception e) {
			json.put("error", e.getMessage());
		}
		return json;
	}

	/**
	 * 将map转换成Json
	 * 
	 * @param bean
	 * @return
	 */
	public static JSONObject mapToJson(Map<String, ? extends Object> map) {
		JSONObject json = new JSONObject();
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			json.put(key, obj);
		}
		return json;
	}

	/**
	 * 将map转换成实体
	 * 
	 * @param map
	 * @param bean
	 * @return
	 */
	public static <T, K, v> T mapToBean(Map<K, v> map, Class<T> bean) {
		T type = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			type = bean.newInstance();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					Object value = map.get(key);
					Method setter = property.getWriteMethod();
					if (checkType(value, property.getPropertyType())) {
						setter.invoke(type, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	private static boolean checkType(Object a, Class<?> b) {
		if (a == null) {
			return true;
		}
		Class<? extends Object> oc = a.getClass();
		if (oc == b || oc.getSuperclass() == b || checkInterfaces(oc.getInterfaces(), b)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean checkInterfaces(Class<?>[] cArray, Class<?> b) {
		boolean tag = false;
		for (Class<?> c : cArray) {
			if (c == b) {
				tag = true;
				break;
			}
		}
		return tag;
	}

	/**
	 * Map List To Bean List
	 * 
	 * @param map
	 * @param bean
	 * @return
	 */
	public static <T, k, v> List<T> mapListToBeanList(List<Map<k, v>> listMap, Class<T> bean) {
		List<T> beanList = new ArrayList<T>(listMap.size());
		for (Map<k, v> map : listMap) {
			T type = null;
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(bean);
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				type = bean.newInstance();
				for (PropertyDescriptor property : propertyDescriptors) {
					String key = property.getName();
					if (map.containsKey(key)) {
						Object value = map.get(key);
						Method setter = property.getWriteMethod();
						setter.invoke(type, value);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			beanList.add(type);
		}

		return beanList;
	}

	public static <T> JSONArray ListBeanToJsonArray(List<T> listBean) {
		JSONArray ja = new JSONArray();
		listBean.forEach((e) -> {
			JSONObject json = beanToJson(e);
			ja.add(json);
		});

		return ja;
	}

	/**
	 * 将实体转换成Map 将key按驼峰规则转换成下划线分隔(字母大写)
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> beanToColumnMap(Object bean) {
		Class<?> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null && !result.equals("")) {
						returnMap.put(camelToUnderline(propertyName), result);
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
			// returnMap.put("error", e.getMessage());
			// returnMap = null;
		}
		return returnMap;
	}

	/**
	 * 将camel Map转成Column Map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> camelMapToColumnMap(Map<String, Object> beanMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			for (String key : beanMap.keySet()) {
				returnMap.put(camelToUnderline(key), beanMap.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 字符串下划线转驼峰
	 * 
	 * @param line
	 * @return
	 */
	public static String underlineToCamel(String line) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			Character c = word.charAt(0);
			sb.append(matcher.start() == 0 ? Character.toLowerCase(c) : Character.toUpperCase(c));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串驼峰转下划线
	 * 
	 * @param line
	 * @return
	 */
	public static String camelToUnderline(String line) {
		if (line == null || "".equals(line)) {
			return "";
		} else if (line.contains("_")) {
			return line.toUpperCase();
		} else if (isAllUpperCase(line)) {
			return line;
		}
		Character c = line.charAt(0);
		String f = line.substring(1);
		line = String.valueOf(c).toUpperCase().concat(f);
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toUpperCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}

	private static boolean isAllUpperCase(String str) {
		// 小写字母范围是从97到122
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 97 && c <= 122) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为整数格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(Object str) {
		if (str == null || str.toString().trim().equals("")) {
			return false;
		}
		Pattern p = Pattern.compile("^[-\\+]?[\\d]*$");
		return p.matcher(str.toString()).matches();
	}

	/**
	 * 
	 * @param type
	 * @param propertyName
	 * @return
	 */
	private static String getFieldAnnotation(Class<?> type, String propertyName) {
		Field field = null;
		String jsonFieldKey = null;
		try {
			field = type.getDeclaredField(propertyName);
			Annotation annotation = field.getAnnotation(JSONField.class);
			JSONField jsonField = (JSONField) annotation;
			jsonFieldKey = jsonField.name();
		} catch (NoSuchFieldException | SecurityException e) {
			jsonFieldKey = propertyName;
		} catch (NullPointerException e) {
			jsonFieldKey = propertyName;
		}
		return jsonFieldKey;
	}

}
