package co.zip.candidate.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.zip.candidate.exception.ZipCoErrorCode;
import co.zip.candidate.model.CreateAccountRequest;
import co.zip.candidate.model.CreateUserRequest;
import co.zip.candidate.model.ErrorResponse;
import co.zip.candidate.model.entity.Account;
import co.zip.candidate.model.entity.User;

@TestMethodOrder(OrderAnnotation.class)
public class UserApiIntegrationTests {

    private static final String USER_CREATE_ENDPOINT = "http://localhost:8080/user";
    private static final String ACCOUNT_CREATE_ENDPOINT = "http://localhost:8080/account";
    private static RestTemplate client = new RestTemplate();
    private static ObjectMapper mapper = new ObjectMapper();
    private static long nowMs = System.currentTimeMillis();
    
    private static CreateUserRequest mockUser(String email, float monthlySalary, float monthlyExpenses) {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail(email);
        request.setMonthlySalary(monthlySalary);
        request.setMonthlyExpenses(monthlyExpenses);
        return request;
    }
    
    private static CreateAccountRequest mockAccount(Long userId, String username, String password) {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setUserId(userId);
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }
    
    @Test
    @Order(1)
    public void createUser() {
        RestTemplate client = new RestTemplate();
        CreateUserRequest sucessUser = mockUser("mockuser" + nowMs + "@example.com", 4000, 1000);
        ResponseEntity<User> resp = client.postForEntity(USER_CREATE_ENDPOINT, sucessUser, User.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        
        User user = resp.getBody();
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(sucessUser.getEmail(), user.getEmail());
        assertEquals(sucessUser.getMonthlySalary(), user.getMonthlySalary());
        assertEquals(sucessUser.getMonthlyExpenses(), user.getMonthlyExpenses());
    }
    
    @Test
    @Order(2)
    public void createUserDuplicateEmail() {
        RestTemplate client = new RestTemplate();
        CreateUserRequest sucessUser = mockUser("mockuser" + nowMs + "@example.com", 4000, 1000);
        try {
            client.postForEntity(USER_CREATE_ENDPOINT, sucessUser, User.class);
            
        } catch (HttpClientErrorException e) {
            ErrorResponse errResp;
            try {
                errResp = mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
                assertEquals(ZipCoErrorCode.DUPLICATE_EMAIL_ADDRESS.getStatus(), e.getStatusCode());
                assertNotNull(errResp);
                assertNotNull(errResp.getCorrelationId());
                assertEquals(ZipCoErrorCode.DUPLICATE_EMAIL_ADDRESS, errResp.getErrorCode());
            } catch (Exception e1) {
                fail(e1);
            } 
        }
        
    }
    
    
    @Test
    @Order(3)
    public void createAccount() {
        CreateUserRequest sucessUser = mockUser("mockuser" + System.currentTimeMillis() +  "@example.com", 4000, 1000);
        ResponseEntity<User> userResp = client.postForEntity(USER_CREATE_ENDPOINT, sucessUser, User.class);
        assertEquals(HttpStatus.OK, userResp.getStatusCode());
        User user = userResp.getBody();
        
        CreateAccountRequest successAcc = mockAccount(user.getId(), "username" + System.currentTimeMillis(), "password");
        ResponseEntity<Account> accResp = client.postForEntity(ACCOUNT_CREATE_ENDPOINT, successAcc, Account.class);
        assertEquals(HttpStatus.OK, accResp.getStatusCode());
        
        Account account = accResp.getBody();
        assertNotNull(account);
        assertNotNull(account.getId());
        assertNotNull(account.getUser());
        assertEquals(user.getId(), account.getUser().getId());
        assertEquals(successAcc.getUsername(), account.getUsername());
        // password should not be propagated to client
        assertNull(account.getPassword());
    }
    
    @Test
    @Order(4)
    public void createAccountInsufficientSavings() {
        CreateUserRequest sucessUser = mockUser("mockuser" + System.currentTimeMillis() +  "@example.com", 1900, 1000);
        ResponseEntity<User> userResp = client.postForEntity(USER_CREATE_ENDPOINT, sucessUser, User.class);
        assertEquals(HttpStatus.OK, userResp.getStatusCode());
        User user = userResp.getBody();
        
        CreateAccountRequest errAcc = mockAccount(user.getId(), "username" + System.currentTimeMillis(), "password");
        try {
            client.postForEntity(ACCOUNT_CREATE_ENDPOINT, errAcc, Account.class);
        } catch (HttpClientErrorException e) {
            ErrorResponse errResp;
            try {
                errResp = mapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
                assertEquals(ZipCoErrorCode.MONTHLY_SAVINGS_THRESHOLD_UNSATISFIED.getStatus(), e.getStatusCode());
                assertNotNull(errResp);
                assertNotNull(errResp.getCorrelationId());
                assertEquals(ZipCoErrorCode.MONTHLY_SAVINGS_THRESHOLD_UNSATISFIED, errResp.getErrorCode());
            } catch (Exception e1) {
                fail(e1);
            } 
        }
        
    }
}
