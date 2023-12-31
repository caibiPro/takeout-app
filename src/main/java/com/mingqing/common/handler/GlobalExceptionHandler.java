package com.mingqing.common.handler;

import com.mingqing.common.exception.CustomException;
import com.mingqing.common.utils.Result;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

  @ExceptionHandler(CustomException.class)
  public Result<?> customRelevanceExceptionHandler(CustomException ex) {
    log.error(ex.getMessage());
    return Result.error(ex.getMessage());
  }
}
