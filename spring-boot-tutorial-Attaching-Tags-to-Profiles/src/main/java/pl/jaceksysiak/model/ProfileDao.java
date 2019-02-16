package pl.jaceksysiak.model;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfileDao extends PagingAndSortingRepository<Profile, Long> {	
	
	Profile findByUser(SiteUser user);
}