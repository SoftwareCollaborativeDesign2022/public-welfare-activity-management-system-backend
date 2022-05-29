package org.saikumo.pwams.controller;

import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "文件接口")
@RequestMapping("/api/file")
public class FileController {
	@Autowired
	UserRepository userRepository;

	private final static String rootPath = "src/main/resources/images/";

	@RequestMapping("/upload")
	public Object uploadFile(Authentication authentication, @RequestParam("file") MultipartFile[] multipartFiles) {
		File fileDir = new File(rootPath);
		if (!fileDir.exists() && !fileDir.isDirectory()) {
			fileDir.mkdirs();
		}
		try {
			if (multipartFiles != null && multipartFiles.length > 0) {
				for (int i = 0; i < multipartFiles.length; i++) {
					try {
						String storagePath = rootPath + UUID.randomUUID() + multipartFiles[i].getOriginalFilename();

						// 3种方法： 第1种
//                        Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);
						// 第2种
						Path path = Paths.get(storagePath);
						Files.write(path, multipartFiles[i].getBytes());

						User user = userRepository.findUserByUsername(authentication.getName());
						List<org.saikumo.pwams.entity.File> fileList = user.getFiles();
						org.saikumo.pwams.entity.File file = new org.saikumo.pwams.entity.File();
						file.setPath(storagePath);
						fileList.add(file);
						user.setFiles(fileList);
						userRepository.save(user);

						// 第3种
						// multipartFiles[i].transferTo(new File(storagePath));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//前端可以通过状态码，判断文件是否上传成功
		return ApiResult.ok();
	}
}

