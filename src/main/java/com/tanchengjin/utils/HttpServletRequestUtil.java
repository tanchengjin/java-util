package com.tanchengjin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * httpServletRequest 简化工具类
 *
 * @author tanchengjin
 * @email 18865477815@163.com
 */
public class HttpServletRequestUtil {
    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return request;
    }

    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        HashMap<String, Object> hds = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            hds.put(header, value);
        }
        return hds;
    }

    /**
     * 请求头转json字符串
     * @param request HttpServletRequest
     * @return string
     */
    public static String headersToJsonString(HttpServletRequest request) {
        String s = "";
        Map<String, Object> headers = getHeaders(request);
        try {
            s = new JsonMapper().writeValueAsString(headers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 参数转json字符串
     * @param request HttpServletRequest
     * @return string
     */
    public static String paramsToJsonString(HttpServletRequest request) {
        String s = "";
        Enumeration<String> headerNames = request.getParameterNames();
        System.out.println(request.getContentType());
        ArrayList<Object> result = new ArrayList<>();
        while (headerNames.hasMoreElements()) {
            HashMap<String, Object> hds = new HashMap<>();
            String header = headerNames.nextElement();
            String value = request.getParameter(header);
            hds.put(header, value);
            result.add(hds);
        }

        try {
            s = new JsonMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    public static String getCurrentUrl() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return httpServletRequest.getRequestURL().toString();
    }

    public static String getCurrentIP() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return httpServletRequest.getRemoteAddr();
    }

    public static String getCurrentMethod() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getMethod();
    }
}