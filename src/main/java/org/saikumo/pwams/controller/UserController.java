package org.saikumo.pwams.controller;

import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.dto.ChangePasswordRequest;
import org.saikumo.pwams.entity.Role;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/changepassword")
	public ApiResult changePassword(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest) {
		return userService.changePassword(authentication, changePasswordRequest.getOldPassword()
				, changePasswordRequest.getNewPassword());
	}
}
