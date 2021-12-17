package co.zip.candidate.repository;

import org.springframework.data.jpa.repository.Query;

import co.zip.candidate.model.entity.User;

public interface UserRepositoryCustom {

    @Query("SELECT u FROM User u WHERE email = ?1")
    User findByEmail(String email);
}
