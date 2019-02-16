package pl.jaceksysiak.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserDao extends PagingAndSortingRepository<SiteUser, Long> {
	
	SiteUser findByEmail(String email);

}
