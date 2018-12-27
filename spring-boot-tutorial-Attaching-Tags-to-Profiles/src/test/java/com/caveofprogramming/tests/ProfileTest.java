package com.caveofprogramming.tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caveofprogramming.model.Interest;
import com.caveofprogramming.model.Profile;
import com.caveofprogramming.model.SiteUser;
import com.caveofprogramming.service.InterestService;
import com.caveofprogramming.service.ProfileService;
import com.caveofprogramming.service.SiteUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
@Transactional
public class ProfileTest {

	@Autowired 
	private SiteUserService siteUserService;
	
	@Autowired 
	private ProfileService profileService;
	
	@Autowired
	private InterestService interestService;
	
	private SiteUser[] users = {
		new SiteUser("a1@wp.pl", "a1"),
		new SiteUser("a2@wp.pl", "a2"),
		new SiteUser("a3@wp.pl", "a3")
	};
	
	private String[][] interests = {
			{"music", "guitar_xxxxxx", "plants"},
			{"music", "music", "philosophy_lkjlkjlk"},
			{"philosophy_lkjlkjlk", "football"}
	};
	
	@Test
	public void testInterests() {
		 
		for(int i=0; i<users.length; i++) {
			
			SiteUser user = users[i];
			String[] interestArray = interests[i];
			
			siteUserService.register(user);
			
			HashSet<Interest> interestSet = new HashSet<>();
			
			for(String interestText: interestArray) {
				
				Interest interest = interestService.createIfNotExists(interestText);
				interestSet.add(interest);
				
				assertNotNull("Interest should not be null", interest);
				assertNotNull("Interest should have ID", interest.getId());
				assertEquals("Text should match", interestText, interest.getName());
			}
			 
			Profile profile = new Profile(user);
			profile.setInterests(interestSet);
			profileService.save(profile);
		
			Profile retrievedProfile = profileService.getUserProfile(user);
		
			assertEquals("Interest sets should match", interestSet, retrievedProfile.getInterests());
		}
	}
}




































