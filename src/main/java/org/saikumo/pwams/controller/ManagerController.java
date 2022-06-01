package org.saikumo.pwams.controller;

import io.swagger.annotations.ApiOperation;
import org.saikumo.pwams.constant.RoleName;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.dto.RegisterRequest;
import org.saikumo.pwams.dto.RegisterStaffRequest;
import org.saikumo.pwams.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	@Autowired
	AuthService authService;

	@ApiOperation("经理注册")
	@PostMapping("/register_staff")
	public ApiResult registerStaff(@Valid @RequestBody RegisterStaffRequest registerStaffRequest) {
		return authService.register(registerStaffRequest.getLoginAccount(), registerStaffRequest.getPassword()
				, registerStaffRequest.getRoleName());
	}
}
