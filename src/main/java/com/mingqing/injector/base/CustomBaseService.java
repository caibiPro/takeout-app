package com.mingqing.injector.base;

import com.baomidou.mybatisplus.extension.service.IService;

public interface CustomBaseService<T> extends IService<T> {

	int deleteByIdWithFill(T entity);
}
