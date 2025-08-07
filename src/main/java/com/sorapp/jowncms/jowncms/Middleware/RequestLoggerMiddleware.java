package com.sorapp.jowncms.jowncms.Middleware;

import com.sorapp.jowncms.jowncms.Service.Log.FileLoger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RequestLoggerMiddleware implements HandlerInterceptor
{

    @Autowired
    FileLoger FileLog;
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggerMiddleware.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String timestamp = LocalDateTime.now().format(formatter);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String clientIp = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        //Url
        String fullUrl = uri + (queryString != null ? "?" + queryString : "");

        //Get log
//        logger.info("REQUEST_START - Time: {} | Method: {} | URL: {} | IP: {} | User-Agent: {} | Referer: {}",
//                timestamp, method, fullUrl, clientIp,
//                userAgent != null ? userAgent.substring(0, Math.min(userAgent.length(), 100)) : "N/A",
//                referer != null ? referer : "N/A");

        //Get log
        FileLog.SaveLog("REQUEST_START - Time: "+timestamp+" | Method: "+method+" | URL: "+fullUrl+" | IP: "+clientIp+" | User-Agent: "+(userAgent != null ? userAgent.substring(0, Math.min(userAgent.length(), 100)) : "N/A")+" | Referer: "+(referer != null ? referer : "N/A")+"");

        // Save datetime
        request.setAttribute("startTime", System.currentTimeMillis());

        return true;

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        Long startTime = (Long) request.getAttribute("startTime");
        long duration = startTime != null ? System.currentTimeMillis() - startTime : 0;

        String timestamp = LocalDateTime.now().format(formatter);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int statusCode = response.getStatus();
        String clientIp = getClientIpAddress(request);

        //Log over request
//        logger.info("REQUEST_END - Time: {} | Method: {} | URL: {} | IP: {} | Status: {} | Duration: {}ms",
//                timestamp, method, uri, clientIp, statusCode, duration);

        //error log
//        if (ex != null) {
//            logger.error("REQUEST_ERROR - Method: {} | URL: {} | IP: {} | Error: {}",
//                    method, uri, clientIp, ex.getMessage(), ex);
//        }

        //request response status code
//        if (statusCode >= 400) {
//            logger.warn("REQUEST_ERROR_STATUS - Method: {} | URL: {} | IP: {} | Status: {}",
//                    method, uri, clientIp, statusCode);
//        }

    }



    private String getClientIpAddress(HttpServletRequest request) {
        String[] headerNames = {
                "X-Forwarded-For",
                "X-Real-IP",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED"
        };

        for (String header : headerNames) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }

}
