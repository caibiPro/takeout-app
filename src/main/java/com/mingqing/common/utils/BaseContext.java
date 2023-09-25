package com.mingqing.common.utils;

public class BaseContext {
	private static ThreadLocal<Long> tl = new ThreadLocal<>();

	public static void setLoginId(Long id){
		tl.set(id);
	}

	public static Long getLoginId(){
		return tl.get();
	}
}
