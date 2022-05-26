package org.saikumo.pwams.service;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.saikumo.pwams.constant.CacheName;
import org.saikumo.pwams.cache.CaffeineCache;
import org.saikumo.pwams.constant.RoleName;
import org.saikumo.pwams.dto.AccessToken;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.entity.Role;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.repository.RoleRepository;
import org.saikumo.pwams.repository.UserRepository;
import org.saikumo.pwams.security.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthService {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	CaffeineCache caffeineCache;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RoleRepository roleRepository;

	public ApiResult login(String loginAccount, String password) {

		log.info("用户登录，用户名为: {}", loginAccount);
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

	public ApiResult register(String loginAccount, String password) {
		User user = userRepository.findUserByUsername(loginAccount);
		//用户名存在
		if(!ObjectUtils.isEmpty(user)){
			return ApiResult.fail("用户名已存在");
		}
		//注册
		user = new User();
		user.setAccountNonExpired(true);
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setUsername(loginAccount);
		user.setPassword(passwordEncoder.encode(password));
		List<Role> roleList = new ArrayList<>();
		Role role = new Role();
		role.setName(RoleName.STUDENT.getRoleName());
		role.setId(RoleName.STUDENT.getId());
		roleList.add(role);
		user.setRoles(roleList);
		userRepository.save(user);
		return ApiResult.ok();
	}
}
