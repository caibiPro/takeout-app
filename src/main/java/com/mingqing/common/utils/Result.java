package com.mingqing.common.utils;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Result<T> {

  private Integer code;

  private String msg;

  private T data;

  private Map<String, Object> map = new HashMap<>();

  protected static <T> Result<T> build(T data) {
    Result<T> result = new Result<T>();
    if (data != null) {
      result.data = data;
    }
    return result;
  }

  public static <T> Result<T> build(Integer code, String msg, T data) {
    Result<T> result = build(data);
    result.code = code;
    result.msg = msg;
    return result;
  }

  public static <T> Result<T> success(T data) {
    return build(1, "success", data);
  }

  public static <T> Result<T> error(String msg) {
    return build(0, msg, null);
  }

  public Result<T> add(String key, Object object) {
    this.map.put(key, object);
    return this;
  }
}
