package org.saikumo.pwams.service;

import lombok.extern.slf4j.Slf4j;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("开始登陆验证，用户名为: {}", username);

		User user = userRepository.findUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在，登陆失败。");
		}
		return user;
	}
}
