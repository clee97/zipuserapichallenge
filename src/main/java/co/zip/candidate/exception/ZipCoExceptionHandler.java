package co.zip.candidate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import co.zip.candidate.filter.ThreadCorrelationId;
import co.zip.candidate.model.ErrorResponse;

/**
 * Intercepts all exceptions and returns a unified error response to the client
 * 
 * @see ErrorResponse
 */
@ControllerAdvice
public class ZipCoExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(ZipCoExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        log.error("An error occured", e);
        if (e instanceof ZipCoException) {
            ZipCoException zipCoEx = (ZipCoException)e;
            ErrorResponse errorResp = new ErrorResponse(zipCoEx.getErrorMessage(), zipCoEx.getErrorCode(), ThreadCorrelationId.getId());
            return new ResponseEntity<ErrorResponse>(errorResp, zipCoEx.getErrorCode().getStatus());
        }
        ErrorResponse errorResp = new ErrorResponse(e.getMessage(), ZipCoErrorCode.INTERNAL_SERVER_ERROR, ThreadCorrelationId.getId());
        return new ResponseEntity<ErrorResponse>(errorResp, ZipCoErrorCode.INTERNAL_SERVER_ERROR.getStatus());
    }
}
