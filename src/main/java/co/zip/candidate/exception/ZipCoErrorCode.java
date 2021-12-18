package co.zip.candidate.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error codes to distinguish types of request errors.
 * 
 * Idea is for client's consuming this to map the error code to a more user friendly error message.
 *
 */
public enum ZipCoErrorCode {
	@JsonProperty("DUPLICATE_EMAIL_ADDRESS")
    DUPLICATE_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("MISSING_EMAIL_FIELD")
    MISSING_EMAIL_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("INVALID_EMAIL_FIELD")
    INVALID_EMAIL_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("MISSING_MONTHLY_SALARY_FIELD")
    MISSING_MONTHLY_SALARY_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("MISSING_MONTHLY_EXPENSES_FIELD")
    MISSING_MONTHLY_EXPENSES_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("INVALID_MONTHLY_SALARY_FIELD")
    INVALID_MONTHLY_SALARY_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("INVALID_MONTHLY_EXPENSES_FIELD")
    INVALID_MONTHLY_EXPENSES_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("USER_NOT_FOUND")
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    
    @JsonProperty("MISSING_USER_FIELD")
    MISSING_USER_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("MISSING_USERNAME_FIELD")
    MISSING_USERNAME_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("MISSING_PASSWORD_FIELD")
    MISSING_PASSWORD_FIELD(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("MONTHLY_SAVINGS_THRESHOLD_UNSATISFIED")
    MONTHLY_SAVINGS_THRESHOLD_UNSATISFIED(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("DUPLICATE_USERNAME")
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST),
    
    @JsonProperty("INTERNAL_SERVER_ERROR")
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
