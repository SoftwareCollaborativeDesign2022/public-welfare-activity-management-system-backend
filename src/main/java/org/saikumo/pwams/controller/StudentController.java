package org.saikumo.pwams.controller;

import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	StudentService studentService;


	@PostMapping("/applymentor")
	public ApiResult applyMentor(Authentication authentication){
		return studentService.applyMentor(authentication);
	}
}
