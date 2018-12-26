package com.caveofprogramming.model;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationDao extends PagingAndSortingRepository<VerificationToken, Long> {
	
	VerificationToken findByToken(String token);
}