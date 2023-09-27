package com.mingqing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishMapper extends BaseMapper<Dish> {

	IPage<DishDTO> selectWithCategory(IPage<?> page, @Param("name") String name);

	DishDTO selectByIdWithFlavor(Long id);
}




