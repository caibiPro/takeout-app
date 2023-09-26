package com.mingqing.controlloer;

import com.mingqing.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
	@Value("${reggie.dump.dish.base-path}")
	private String basePath;

	@PostMapping("/upload")
	public Result<?> upload(@RequestParam("file") MultipartFile uploadFile) throws IOException {
		log.info("获取文件：{}", uploadFile.toString());
		
		File dir = new File(basePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String originalFilename = uploadFile.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String filename = UUID.randomUUID() + suffix;

		uploadFile.transferTo(new File(basePath + filename));
		log.info("转存文件至：{}", basePath + filename);
		return Result.success(filename);
	}

	@GetMapping("/download")
	public void download(String name, HttpServletResponse response) {
		byte[] buffer = new byte[1024];
		int bytesRead;
		try {
			FileInputStream fis = new FileInputStream(new File(basePath + name));
			while ((bytesRead = fis.read(buffer)) != -1) {
				response.getOutputStream().write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
