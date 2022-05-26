package org.saikumo.pwams.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
	@NotBlank(message = "登陆用户名为空")
	private String loginAccount;

	@NotBlank(message = "登陆密码为空")
	private String password;
}
