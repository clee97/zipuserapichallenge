package co.zip.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.zip.candidate.model.CreateAccountRequest;
import co.zip.candidate.model.PageableResponse;
import co.zip.candidate.model.entity.Account;
import co.zip.candidate.service.AccountService;

/**
 * <p>
 * Account endpoints required by API Requirements section
 * </p>
 * 
 * <ol>
 *     <li>Create account</li>
 *     <li>List accounts</li>
 * </ol>
 */
@RestController
@RequestMapping(value="/account")
public class AccountController {

    private AccountService accountService;
    
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }
    
    @GetMapping
    public PageableResponse<Account> listUsers(
        @RequestParam(name="page", defaultValue="0") int page, 
        @RequestParam(name="size", defaultValue="20") int size
    ) {
        PageableResponse<Account> respData = accountService.listAccounts(page, size);
        if (respData.getNextPage() != null) {
            String nextLink = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/account")
                .queryParam("page", respData.getNextPage())
                .queryParam("size", respData.getPageSize())
                .build()
                .toString();
            respData.setNextLink(nextLink);
        }
        if (respData.getPrevPage() != null) {
            String prevLink = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/account")
                    .queryParam("page", respData.getPrevPage())
                    .queryParam("size", respData.getPageSize())
                    .build()
                    .toString();
            respData.setPrevLink(prevLink);
        }
        return respData;
    }
}
