package com.campingmapping.team4.spring.utils.service;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.campingmapping.team4.spring.t401member.model.dao.repository.RoleRepository;
import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.dto.AuthenticationRequest;
import com.campingmapping.team4.spring.t401member.model.dto.AuthenticationResponse;
import com.campingmapping.team4.spring.t401member.model.dto.RegisterRequest;
import com.campingmapping.team4.spring.t401member.model.entity.Role;
import com.campingmapping.team4.spring.t401member.model.entity.UserDetail;
import com.campingmapping.team4.spring.t401member.model.entity.UserName;
import com.campingmapping.team4.spring.t401member.model.entity.UserPrivacy;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.utils.config.MyConstants;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;

	// 註冊
	@Transactional
	public Boolean register(RegisterRequest request) {
		try {
			Role roleUser = roleRepository.findByName("USER").get();
			// Name
			UserName userName = UserName.builder()
					.firstname("")
					.lastname("")
					.build();
			// Privacy
			UserPrivacy userPrivacy = UserPrivacy.builder()
					.address("")
					.birthday(null)
					.phone("")
					.build();
			// Detail
			UserDetail userDetail = UserDetail.builder()
					.nickname(request.email())
					.exp(1L)
					.level(1)
					.point(100L)
					.gender(0)
					.registerdata(new Date())
					.subscribed(false)
					.shot("https://storage.googleapis.com/morariphoto/defaultshot")
					.about("暫時沒有留下什麼")
					.build();
			UserProfiles userProfiles = UserProfiles.builder()
					.email(request.email())
					.password(passwordEncoder.encode(request.password()))
					.uid(UUID.randomUUID())
					.userdetail(userDetail)
					.usernames(userName)
					.userprivacy(userPrivacy)
					.accountnonexpired(true)
					.iscredentialsnonexpired(true)
					.isenabled(true)
					.accountnonlocked(true)
					.build();
			userProfiles.getRoles().add(roleUser);
			userRepository.save(userProfiles);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 登入
	@Transactional
	public Boolean authenticate(AuthenticationRequest request, HttpServletResponse response) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.email(),
							request.password()));
			UserProfiles userProfiles = userRepository.findByEmail(request.email()).orElseThrow();
			 // 權限是否正常
			 if (!(userProfiles.getAccountnonexpired() && userProfiles.getAccountnonlocked() && userProfiles.isEnabled()
			 && userProfiles.isCredentialsNonExpired())) {
			   jwtService.removeToken(response);
			   response.sendRedirect("/morari/login?error=user_not_authorized");
			 }
			AuthenticationResponse authenticationResponse = jwtService.generateToken(userProfiles,
					request.rememberMe());
			jwtService.refreshTokenToCookie(response, authenticationResponse);
			return true;
		} 
		catch (LockedException e) {
			// e.printStackTrace();
			return null;
		}
		catch (AccountExpiredException e) {
			// e.printStackTrace();
			return null;
		}
		catch (DisabledException e) {
			// e.printStackTrace();
			return null;
		}
		catch (CredentialsExpiredException e) {
			// e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	// 登入狀態
	public Boolean loginstate(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String cookiejwt = null;
		Boolean islogin = false;
		if (cookies != null) {
			cookiejwt = jwtService.getToken(cookies, MyConstants.JWT_COOKIE_NAME);
		}
		if (cookiejwt == null || cookiejwt.isEmpty()) {
			return false;
		}
		try {
			String userEmail = jwtService.extractUsername(cookiejwt);
			if (userEmail != null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				islogin = jwtService.isTokenValid(cookiejwt, userDetails);
			}
		} catch (Exception e) {
			islogin = false;
		}
		return islogin;
	}

	@Transactional
	public List<Role> getroles() {		
		return roleRepository.findAll();
	}
}
