package co.zip.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.zip.candidate.model.CreateUserRequest;
import co.zip.candidate.model.PageableResponse;
import co.zip.candidate.model.entity.User;
import co.zip.candidate.service.UserService;

/**
 * <p>
 * User endpoints required by API Requirements section
 * </p>
 * 
 * <ol>
 *     <li>Create user</li>
 *     <li>Get user</li>
 *     <li>List users</li>
 * </ol>
 */
@RestController
@RequestMapping(value="/user")
public class UserController {

    private UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
    
    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }
    
    @GetMapping
    public PageableResponse<User> listUsers(
        @RequestParam(name="page", defaultValue="0") int page, 
        @RequestParam(name="size", defaultValue="20") int size
    ) {
        PageableResponse<User> respData = userService.listUsers(page, size);
        if (respData.getNextPage() != null) {
            String nextLink = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user")
                .queryParam("page", respData.getNextPage())
                .queryParam("size", respData.getPageSize())
                .build()
                .toString();
            respData.setNextLink(nextLink);
        }
        if (respData.getPrevPage() != null) {
            String prevLink = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user")
                    .queryParam("page", respData.getPrevPage())
                    .queryParam("size", respData.getPageSize())
                    .build()
                    .toString();
            respData.setPrevLink(prevLink);
        }
        return respData;
    }
}
