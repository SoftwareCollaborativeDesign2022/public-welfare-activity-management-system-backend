package org.saikumo.pwams.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户接口")
public class UserController {
	@Autowired
	UserService userService;

	@ApiOperation("修改密码")
	@PostMapping("/changepassword")
	public ApiResult changePassword(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest) {
		return userService.changePassword(authentication, changePasswordRequest.getOldPassword()
				, changePasswordRequest.getNewPassword());
	}
}
