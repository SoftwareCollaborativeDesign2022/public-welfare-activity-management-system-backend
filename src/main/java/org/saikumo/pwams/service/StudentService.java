package org.saikumo.pwams.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.saikumo.pwams.constant.RoleName;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.entity.Role;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {
	@Autowired
	UserRepository userRepository;

	public ApiResult applyMentor(Authentication authentication) {
		log.info("申请导师，用户名为：{}", authentication.getName());
		User user = userRepository.findUserByUsername(authentication.getName());

		val authorities = user.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(RoleName.MENTOR.getRoleName())) {
				return ApiResult.fail("已拥有导师角色");
			}
		}

		if (user == null) {
			return ApiResult.fail("数据库错误");
		}

		List<Role> roleList = user.getRoles();
		Role newRole = new Role();
		newRole.setId(RoleName.MENTOR.getId());
		newRole.setName(RoleName.MENTOR.getRoleName());
		roleList.add(newRole);
		user.setRoles(roleList);
		userRepository.save(user);
		return ApiResult.ok();
	}
}
