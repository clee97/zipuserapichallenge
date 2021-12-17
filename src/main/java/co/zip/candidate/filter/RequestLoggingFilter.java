package co.zip.candidate.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Order(FilterOrder.REQUEST_LOG)
public class RequestLoggingFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        CachedBodyHttpServletRequest cachedReq = new CachedBodyHttpServletRequest(httpReq);
        
        StringBuilder logBuilder = new StringBuilder("REQUEST ");
        logBuilder.append("[method=").append(httpReq.getMethod()).append("] ");
        logBuilder.append("[path=").append(httpReq.getRequestURI()).append("] ");
        if (StringUtils.isNotBlank(httpReq.getQueryString())) {
            logBuilder.append("[query=").append(httpReq.getQueryString()).append("] ");
        }
        
        logBuilder.append("[correlationId=").append(ThreadCorrelationId.getId()).append("] ");
        
        // don't log multi-part binary bodies
        if (!httpReq.getMethod().equals(HttpMethod.GET.name()) 
                && !httpReq.getHeader("Content-Type").contains("multipart/form-data") 
                && StringUtils.isNotBlank(cachedReq.getRequestBody())) {
            logBuilder.append("body = ").append(cachedReq.getRequestBody());
        }
        
        log.info(logBuilder.toString());
        
        filterChain.doFilter(cachedReq, response);
    }

}
