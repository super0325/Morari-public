package com.campingmapping.team4.spring.utils.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.campingmapping.team4.spring.t401member.model.dto.AuthenticationResponse;
import com.campingmapping.team4.spring.utils.service.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // 進入驗證程序
    AuthenticationResponse authenticationResponse;
    // 取得access token和refresh token
    Cookie[] cookies = request.getCookies();
    String accessToken = null;
    String refreshToken = null;
    if (cookies != null) {
      accessToken = jwtService.getToken(cookies, MyConstants.JWT_COOKIE_NAME);
      refreshToken = jwtService.getToken(cookies, MyConstants.JWT_REFRESH_COOKIE_NAME);
    }
    if (accessToken == null || accessToken.isEmpty()) {
      // JWT空 跳轉登入畫面
      filterChain.doFilter(request, response);
      return;
    }
    if (jwtService.isTokenExpired(accessToken)) {
      if (jwtService.isTokenExpired(refreshToken)) {
        // 如果access token和refresh token都過期，刪除token
        jwtService.removeToken(response);
        // 跳轉登入
        filterChain.doFilter(request, response);
        return;
      } else {
        // 檢查refresh token是否有效(過期=無效不需在驗證一次是否過期)
        Boolean remember = (Boolean) jwtService.extractAllClaims(refreshToken).get("remember");
        String email = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (jwtService.isTokenValid(refreshToken, userDetails)) {

          // 權限是否正常
          if (!(userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked() && userDetails.isEnabled()
              && userDetails.isCredentialsNonExpired())) {
            jwtService.removeToken(response);
            response.sendRedirect("/morari/login?error=user_not_authorized");
            return;
          }

          // 生成新令牌
          authenticationResponse = jwtService.generateToken(userDetails, remember);
          // 刷新Cookie
          jwtService.refreshTokenToCookie(response, authenticationResponse);
          // 設置安全上下文
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
          authToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
          // 繼續導向
          filterChain.doFilter(request, response);
          return;
        } else {
          // access token過期refresh token驗證失敗
          // 清空有JWT的Cookie
          jwtService.removeToken(response);
          // 跳轉登入
          filterChain.doFilter(request, response);
          return;
        }
      }
    }
    String email = jwtService.extractUsername(accessToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    if (jwtService.isTokenValid(accessToken, userDetails)) {
      // 設置安全上下文
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities());
      authToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
      filterChain.doFilter(request, response);
      return;
    } else {
      // access token驗證失敗
      filterChain.doFilter(request, response);
      return;
    }

  }
}
