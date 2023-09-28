package com.mingqing.injector.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomBaseMapper<T> extends BaseMapper<T> {

	/**
	 * 以下定义的是内置的选装件
	 */
	int deleteByIdWithFill(T entity);

	/**
	 * 以下为自定义
	 * 如果要自动填充，@{@code Param}(xx) xx参数名必须是 list/collection/array 3个的其中之一
	 * @param batchList
	 * @return
	 */
	int mysqlInsertAllBatch(@Param("list") List<T> batchList);

}
