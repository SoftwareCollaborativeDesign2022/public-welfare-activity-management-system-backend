package org.saikumo.pwams.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.dto.LoginRequest;
import org.saikumo.pwams.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "登录认证接口")
public class AuthController {
	@Autowired
	AuthService authService;

	@ApiOperation("登录")
	@PostMapping("/login")
	public ApiResult login(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest.getLoginAccount(), loginRequest.getPassword());
	}
}
