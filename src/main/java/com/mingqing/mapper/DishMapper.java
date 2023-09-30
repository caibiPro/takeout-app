package com.mingqing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mingqing.injector.base.CustomBaseMapper;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DishMapper extends CustomBaseMapper<Dish> {

	IPage<DishDTO> selectWithCategory(IPage<?> page, @Param("name") String name);

	DishDTO selectByIdWithFlavor(Long id);

	List<DishDTO> selectWithFlavors(Dish dish);
}




