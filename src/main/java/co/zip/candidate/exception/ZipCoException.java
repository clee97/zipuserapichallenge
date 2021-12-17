package co.zip.candidate.exception;

public class ZipCoException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private ZipCoErrorCode errorCode;
    
    private String errorMessage;
    
    public ZipCoException(String errorMessage, ZipCoErrorCode errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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
    
}
