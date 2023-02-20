package com.campingmapping.team4.spring.utils.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.campingmapping.team4.spring.t401member.model.dao.repository.UserRepository;
import com.campingmapping.team4.spring.t401member.model.dto.AuthenticationResponse;
import com.campingmapping.team4.spring.t401member.model.entity.AuthProvider;
import com.campingmapping.team4.spring.t401member.model.entity.OAuth2Request;
import com.campingmapping.team4.spring.t401member.model.entity.UserProfiles;
import com.campingmapping.team4.spring.utils.service.attributemapper.AttributeMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// OAuth2 成功登入執行
@Component
@RequiredArgsConstructor
public class OAuth2AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final UserRepository userRepository;
  private final JwtService jwtService;

  private final AttributeMapper attributeMapper;
  private final AuthAccountService authAccountService;
  // private final AuthAccountMapper authAccountMapper;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    // 尋找是哪的服務;
    String uri = request.getRequestURI();
    String[] parts = uri.split("/");
    String provider = parts[parts.length - 1];
    AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    // 裝填DTO
    OAuth2Request oAuth2Request = attributeMapper.mapToUser(authProvider, oAuth2User.getAttributes());
    // 是否創建帳戶
    // UserProfiles user = authAccountService.createIfFirst(oAuth2Request);
    UserProfiles userProfiles = authAccountService.createIfFirst(oAuth2Request);
    // 權限是否正常
    if (!(userProfiles.getAccountnonexpired() && userProfiles.getAccountnonlocked() && userProfiles.isEnabled()
    && userProfiles.isCredentialsNonExpired())) {
      jwtService.removeToken(response);
      response.sendRedirect("/morari/login?error=user_not_authorized");
      return;
    }
    // 拿取用戶資料
    UserProfiles getuserProfiles = getOAuthUser((OAuth2User) authentication.getPrincipal());
    AuthenticationResponse authenticationResponse = jwtService.generateToken(getuserProfiles,
        false);
    jwtService.refreshTokenToCookie(response, authenticationResponse);
    // 重新導向
    response.sendRedirect("/morari/home");
  }

  private UserProfiles getOAuthUser(OAuth2User oAuth2User) {
    return userRepository.findByEmail(oAuth2User.getAttribute("email")).orElseThrow();
  }
}
