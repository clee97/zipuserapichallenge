package co.zip.candidate.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.zip.candidate.model.entity.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, AccountRepositoryCustom {

}
