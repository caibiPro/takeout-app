package com.mingqing.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class FieldAutoFillConfig implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill ...");
		this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill ...");
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
	}
}
