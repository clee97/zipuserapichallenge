package co.zip.candidate.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Microservices in production must have the ability to trace requests and errors. 
 * 
 * This filter simply sets a unique correlation id for the current thread so that it appears during the request / response logs
 */
@Component
@Order(FilterOrder.CORRELATION_ID)
public class CorrelationIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        String incomingCorrId = httpReq.getHeader(ThreadCorrelationId.CORRELATION_ID_HEADER);
        if (StringUtils.isNotBlank(incomingCorrId)) {
            ThreadCorrelationId.setId(incomingCorrId);
        } else {
            ThreadCorrelationId.generate();
        }
        MDC.put(ThreadCorrelationId.CORRELATION_ID_HEADER, ThreadCorrelationId.getId());
        filterChain.doFilter(httpReq, response);
    }

}
