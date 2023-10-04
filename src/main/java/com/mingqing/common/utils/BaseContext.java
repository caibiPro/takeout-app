package com.mingqing.common.utils;

public class BaseContext {

  private static final ThreadLocal<Long> tl = new ThreadLocal<>();

  public static Long getLoginId() {
    return tl.get();
  }

  public static void setLoginId(Long id) {
    tl.set(id);
  }
}
