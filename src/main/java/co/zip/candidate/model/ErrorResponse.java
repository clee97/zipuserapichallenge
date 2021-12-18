package co.zip.candidate.model;

import co.zip.candidate.exception.ZipCoErrorCode;
import co.zip.candidate.exception.ZipCoExceptionHandler;

/**
 * A unified error response structure. This object will be returned to the client whenever an error occurs.
 * 
 * @see ZipCoExceptionHandler
 */
public class ErrorResponse {

    private ZipCoErrorCode errorCode;
    
    private String errorMessage;
    
    private String correlationId;
    
    public ErrorResponse() {}
    
    public ErrorResponse(String errorMessage, ZipCoErrorCode errorCode, String correlationId) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.correlationId = correlationId;
    }

    public ZipCoErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ZipCoErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
    
}
