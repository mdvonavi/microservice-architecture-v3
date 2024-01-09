package ru.skillbox.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Log request details
        String body = "";
        //        List<String> methods = Arrays.asList("PUT", "POST");
        //        if (methods.contains(request.getMethod().toUpperCase(Locale.ROOT)))
        //        {
        //            Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
        //            body =  s.hasNext() ? s.next() : "";
        //        }
        logger.info("Received request: {} {} {} from {}", request.getMethod(), request.getRequestURI(), body, request.getRemoteAddr());
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws Exception {
        // Log response details
        logger.info(
                "Sent response: {} {} with status {} and exception {}",
                request.getMethod(), request.getRequestURI(), response.getStatus(), ex
        );
    }
}