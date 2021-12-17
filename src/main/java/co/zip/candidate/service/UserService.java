package co.zip.candidate.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.zip.candidate.exception.ZipCoErrorCode;
import co.zip.candidate.exception.ZipCoException;
import co.zip.candidate.model.CreateUserRequest;
import co.zip.candidate.model.PageableResponse;
import co.zip.candidate.model.entity.User;
import co.zip.candidate.repository.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    private User validateUserCreationRequest(CreateUserRequest request) {
        if (StringUtils.isBlank(request.getEmail())) {
            throw new ZipCoException("Email address is required", ZipCoErrorCode.MISSING_EMAIL_FIELD);
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(request.getEmail())) {
            throw new ZipCoException("Invalid email format", ZipCoErrorCode.INVALID_EMAIL_FIELD);
        }
        User userWithSameEmail = userRepository.findByEmail(request.getEmail());
        if (userWithSameEmail != null) {
            throw new ZipCoException("Email has been taken", ZipCoErrorCode.DUPLICATE_EMAIL_ADDRESS);
        }
        if (request.getMonthlySalary() == null) {
            throw new ZipCoException("Monthly salary is required", ZipCoErrorCode.MISSING_MONTHLY_SALARY_FIELD);
        }
        if (request.getMonthlySalary() < 0) {
            throw new ZipCoException("Monthly salary must be positive", ZipCoErrorCode.INVALID_MONTHLY_SALARY_FIELD);
        }
        if (request.getMonthlyExpenses() == null) {
            throw new ZipCoException("Monthly expenses is required", ZipCoErrorCode.MISSING_MONTHLY_EXPENSES_FIELD);
        }
        if (request.getMonthlyExpenses() < 0) {
            throw new ZipCoException("Monthly expenses must be positive", ZipCoErrorCode.INVALID_MONTHLY_EXPENSES_FIELD);
        }
        
        User userEntity = new User();
        userEntity.setEmail(request.getEmail());
        userEntity.setMonthlySalary(request.getMonthlySalary());
        userEntity.setMonthlyExpenses(request.getMonthlyExpenses());
        
        return userEntity;
    }
    public User createUser(CreateUserRequest request) {
        User userEntity = validateUserCreationRequest(request);
        
        return userRepository.save(userEntity);
    }
    
    public User getUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ZipCoException("User does not exist", ZipCoErrorCode.USER_NOT_FOUND));
        return existingUser;
    }
    
    public PageableResponse<User> listUsers(int page, int size) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
        
        return new PageableResponse<>(userPage);
    }

}
