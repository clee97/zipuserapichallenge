package co.zip.candidate.repository;

import org.springframework.data.jpa.repository.Query;

import co.zip.candidate.model.entity.Account;

public interface AccountRepositoryCustom {

    @Query("SELECT a FROM Account a WHERE username = ?1")
    Account findByUsername(String username);
}
