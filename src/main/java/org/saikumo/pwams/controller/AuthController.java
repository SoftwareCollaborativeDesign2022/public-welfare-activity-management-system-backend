package org.saikumo.pwams.controller;

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
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping("/login")
	public ApiResult login(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest.getLoginAccount(), loginRequest.getPassword());
	}
}
