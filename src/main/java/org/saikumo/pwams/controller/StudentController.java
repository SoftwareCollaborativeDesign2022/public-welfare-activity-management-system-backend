package org.saikumo.pwams.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
@Api(tags = "学生接口")
public class StudentController {
	@Autowired
	StudentService studentService;

	@ApiOperation("申请导师")
	@PostMapping("/applymentor")
	public ApiResult applyMentor(Authentication authentication){
		return studentService.applyMentor(authentication);
	}
}
