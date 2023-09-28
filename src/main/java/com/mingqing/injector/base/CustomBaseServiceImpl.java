package com.mingqing.injector.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class CustomBaseServiceImpl<M extends CustomBaseMapper<T>, T> extends ServiceImpl<M, T> implements CustomBaseService<T> {

	@Override
	public int deleteByIdWithFill(T entity) {
		return baseMapper.deleteByIdWithFill(entity);
	}
}
