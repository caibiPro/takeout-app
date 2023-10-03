package com.mingqing.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mingqing.common.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill ....");
		this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "orderTime", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "checkoutTime", LocalDateTime.class, LocalDateTime.now());

		if (BaseContext.getLoginId() != null) {
			this.strictInsertFill(metaObject, "createUser", Long.class, BaseContext.getLoginId());
			this.strictInsertFill(metaObject, "updateUser", Long.class, BaseContext.getLoginId());
		}

	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill ....");
//		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
//		this.strictUpdateFill(metaObject, "updateUser", Long.class, BaseContext.getLoginId());
//		metaObject.setValue("updateTime", LocalDateTime.now());
//		metaObject.setValue("updateUser", BaseContext.getLoginId());
		this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
		this.setFieldValByName("checkoutTime", LocalDateTime.now(), metaObject);
		if (BaseContext.getLoginId() != null) {
			this.setFieldValByName("updateUser", BaseContext.getLoginId(), metaObject);
		}
	}
}
