package org.saikumo.pwams.service;

import lombok.extern.slf4j.Slf4j;
import org.saikumo.pwams.constant.CacheName;
import org.saikumo.pwams.cache.CaffeineCache;
import org.saikumo.pwams.dto.AccessToken;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.security.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	CaffeineCache caffeineCache;

	public ApiResult login(String loginAccount, String password) {

		log.debug("进入login方法");
		// 1 创建UsernamePasswordAuthenticationToken
		UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(loginAccount, password);
		// 2 认证
		Authentication authentication = this.authenticationManager.authenticate(usernameAuthentication);
		// 3 保存认证信息
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 4 生成自定义token
		AccessToken accessToken = jwtProvider.createToken((UserDetails) authentication.getPrincipal());

		User userDetail = (User) authentication.getPrincipal();
		// 放入缓存
		caffeineCache.put(CacheName.USER, userDetail.getUsername(), userDetail);
		return ApiResult.ok(accessToken);
	}
}
