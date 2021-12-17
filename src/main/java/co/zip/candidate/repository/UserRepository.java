package co.zip.candidate.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.zip.candidate.model.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, UserRepositoryCustom {

}
