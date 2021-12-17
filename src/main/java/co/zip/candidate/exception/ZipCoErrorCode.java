package co.zip.candidate.exception;

import org.springframework.http.HttpStatus;

/**
 * Error codes to distinguish types of request errors.
 * 
 * Idea is for client's consuming this to map the error code to a more user friendly error message.
 *
 */
public enum ZipCoErrorCode {
    DUPLICATE_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST),
    MISSING_EMAIL_FIELD(HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FIELD(HttpStatus.BAD_REQUEST),
    MISSING_MONTHLY_SALARY_FIELD(HttpStatus.BAD_REQUEST),
    MISSING_MONTHLY_EXPENSES_FIELD(HttpStatus.BAD_REQUEST),
    INVALID_MONTHLY_SALARY_FIELD(HttpStatus.BAD_REQUEST),
    INVALID_MONTHLY_EXPENSES_FIELD(HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    MISSING_USER_FIELD(HttpStatus.BAD_REQUEST),
    MISSING_USERNAME_FIELD(HttpStatus.BAD_REQUEST),
    MISSING_PASSWORD_FIELD(HttpStatus.BAD_REQUEST),
    MONTHLY_SAVINGS_THRESHOLD_UNSATISFIED(HttpStatus.BAD_REQUEST),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);
    
    private HttpStatus status;
    
    private ZipCoErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
}
