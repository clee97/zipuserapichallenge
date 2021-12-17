package co.zip.candidate.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.zip.candidate.exception.ZipCoErrorCode;
import co.zip.candidate.exception.ZipCoException;
import co.zip.candidate.model.CreateAccountRequest;
import co.zip.candidate.model.PageableResponse;
import co.zip.candidate.model.entity.Account;
import co.zip.candidate.model.entity.User;
import co.zip.candidate.repository.AccountRepository;
import co.zip.candidate.repository.UserRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    
    private UserRepository userRepositiory;
    
    /**
     * Denotes the minumum amount of monthly savings a user needs to meet in order to be eligible to create an account
     */
    private static final int MONTHLY_SAVINGS_THRESHOLD = 1000;
    
    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepositiory) {
        this.accountRepository = accountRepository;
        this.userRepositiory = userRepositiory;
    }
    
    private Account validateAccountCreationRequest(CreateAccountRequest request) {
        if (request.getUserId() == null) {
            throw new ZipCoException("Missing user", ZipCoErrorCode.MISSING_USER_FIELD);
        }
        if (StringUtils.isBlank(request.getUsername())) {
            throw new ZipCoException("Missing name", ZipCoErrorCode.MISSING_USERNAME_FIELD);
        }
        Account accountWithSameUsername = accountRepository.findByUsername(request.getUsername());
        if (accountWithSameUsername != null) {
            throw new ZipCoException("Username has been taken", ZipCoErrorCode.DUPLICATE_USERNAME);
        }
        
        User existingUser = userRepositiory.findById(request.getUserId())
                .orElseThrow(() -> new ZipCoException("User does not exist", ZipCoErrorCode.USER_NOT_FOUND));
        
        Float userSavings = existingUser.getMonthlySalary() - existingUser.getMonthlyExpenses();
        if (userSavings < MONTHLY_SAVINGS_THRESHOLD) {
            throw new ZipCoException(
                "User does not meet the monthly savings threshold of $" + MONTHLY_SAVINGS_THRESHOLD, 
                ZipCoErrorCode.MONTHLY_SAVINGS_THRESHOLD_UNSATISFIED
            );
        }
        
        Account account = new Account();
        account.setUser(existingUser);
        account.setUsername(request.getUsername());
        // store password as sha256 hex string for security purposes
        account.setPassword(DigestUtils.sha256Hex(request.getPassword()));
        
        return account;
    }
    
    public Account createAccount(CreateAccountRequest request) {
        Account account = validateAccountCreationRequest(request);
        
        return accountRepository.save(account);
    }
    
    public PageableResponse<Account> listAccounts(int page, int size) {
        Page<Account> accountPage = accountRepository.findAll(PageRequest.of(page, size));
        
        return new PageableResponse<Account>(accountPage);
    }
}
