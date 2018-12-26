package com.caveofprogramming.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caveofprogramming.model.SiteUser;
import com.caveofprogramming.model.SiteUserDao;
import com.caveofprogramming.model.TokenType;
import com.caveofprogramming.model.VerificationDao;
import com.caveofprogramming.model.VerificationToken;

@Service
public class SiteUserService implements UserDetailsService {
	
	@Autowired
	private SiteUserDao siteUserDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	  
	public void register(SiteUser user) {
		
	  user.setRole("ROLE_USER");
	  //user.setPassword(passwordEncoder.encode(user.getPassword()));
	  siteUserDao.save(user);
	}
	
	public void save(SiteUser user) {
		
		siteUserDao.save(user);
	}

	@Autowired
	private VerificationDao verificationDao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		SiteUser user = siteUserDao.findByEmail(email);
		 
		if(user == null) {
			return null;
		}
		
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
		
		String password = user.getPassword();
		
		Boolean enabled = user.getEnabled(); 
		 
		return new User(email, password, enabled, true, true, true, auth);
	}

	public String createEmailVerificationToken(SiteUser user) {
		
		VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user, TokenType.REGISTRATION);
		
		verificationDao.save(token);
		return token.getToken();
	}

	public VerificationToken getVerificationToken(String token) {
		
		return verificationDao.findByToken(token);
	}

	public void deleteToken(VerificationToken token) {
		
		verificationDao.delete(token);
	}

	public SiteUser get(String email) {
		return siteUserDao.findByEmail(email);
	}

	public Optional<SiteUser> get(Long id) {
		return siteUserDao.findById(id);   //.findOne(id);
	}
}




































