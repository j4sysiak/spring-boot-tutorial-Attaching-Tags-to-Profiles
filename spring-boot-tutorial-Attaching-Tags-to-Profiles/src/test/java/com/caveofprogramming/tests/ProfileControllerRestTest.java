package com.caveofprogramming.tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.caveofprogramming.model.Interest;
import com.caveofprogramming.model.Profile;
import com.caveofprogramming.model.SiteUser;
import com.caveofprogramming.service.InterestService;
import com.caveofprogramming.service.ProfileService;
import com.caveofprogramming.service.SiteUserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
@Transactional
public class ProfileControllerRestTest {
	
	@Autowired
	private SiteUserService siteUserService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private InterestService interestService;
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@WithMockUser(username = "p1@wp.pl")
	public void testSaveAndDeleteInterest() throws Exception {
		
		String interestText = "some interest_here";
		
		mockMvc.perform(post("/save-interest").param("name", interestText)).andExpect(status().isOk());
		
		Interest interest = interestService.get(interestText);
		assertNotNull("Interest should exist", interest);
		assertEquals("Retrieved interest text should match", interestText, interest.getName());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		SiteUser user = siteUserService.get(email);
		
		Profile profile = profileService.getUserProfile(user);
		assertTrue("Profile should contain interest", profile.getInterests().contains(new Interest(interestText)));
		
		
		mockMvc.perform(post("/delete-interest").param("name", interestText)).andExpect(status().isOk());

		profile = profileService.getUserProfile(user);
		assertFalse("Profile should not contain interest", profile.getInterests().contains(new Interest(interestText)));
	}

}
































