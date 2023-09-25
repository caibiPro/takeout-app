package com.mingqing.common.handler;

import com.mingqing.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Result<?> sqlUniqueExceptionHandler(SQLIntegrityConstraintViolationException ex) {
		String errMsg = ex.getMessage();
		log.error(errMsg);
		if (errMsg.contains("Duplicate entry")) {
			String[] splitMsg = errMsg.split(" ");
			String username = splitMsg[2];
			return Result.error("用户名" + username + "已存在");
		}
		return Result.error("未知错误");
	}
}
