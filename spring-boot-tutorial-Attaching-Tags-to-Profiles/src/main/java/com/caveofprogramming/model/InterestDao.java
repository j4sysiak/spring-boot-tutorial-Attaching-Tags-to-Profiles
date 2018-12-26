package com.caveofprogramming.model;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestDao extends PagingAndSortingRepository<Interest, Long> {
	
	Interest findOneByName(String name);
}
