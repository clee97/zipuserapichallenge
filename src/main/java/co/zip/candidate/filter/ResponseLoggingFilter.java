package co.zip.candidate.filter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ControllerAdvice
public class ResponseLoggingFilter implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(ResponseLoggingFilter.class);
    
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object body, 
        MethodParameter returnType, 
        MediaType selectedContentType, 
        Class<? extends HttpMessageConverter<?>> selectedConverterType, 
        ServerHttpRequest request,
        ServerHttpResponse response
    ) {
        HttpServletResponse httpResp = ((ServletServerHttpResponse) response).getServletResponse();
        
        StringBuilder logBuilder = new StringBuilder("RESPONSE ");
        logBuilder.append("[status=").append(httpResp.getStatus()).append("] ");
        logBuilder.append("[method=").append(request.getMethod()).append("] ");
        logBuilder.append("[path=").append(request.getURI().getPath()).append("] ");
        if (StringUtils.isNotBlank(request.getURI().getQuery())) {
            logBuilder.append("[query=").append(request.getURI().getQuery()).append("] ");
        }
        
        logBuilder.append("[correlationId=").append(ThreadCorrelationId.getId()).append("] ");
        
        if (body != null) {
            ContentDisposition contentDisposition = response.getHeaders().getContentDisposition();
            if (StringUtils.isNotBlank(contentDisposition.toString())) {
                logBuilder.append("body = ").append(contentDisposition.toString());
            } else {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    logBuilder.append("body = ").append(mapper.writeValueAsString(body));
                } catch (JsonProcessingException e) {
                    log.info("Unable to stringify response body: " + e.getMessage());
                }
            }
            
        }
        log.info(logBuilder.toString());
        
        return body;
    }

}
