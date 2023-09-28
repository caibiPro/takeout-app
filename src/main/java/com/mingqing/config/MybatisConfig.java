package com.mingqing.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mingqing.injector.base.CustomSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return mybatisPlusInterceptor;
	}

	/**
	 * 自定义SqlInjector，包含自定义的全局方法
	 */
	@Bean
	public CustomSqlInjector customSqlInjector() {
		return new CustomSqlInjector();
	}
}
