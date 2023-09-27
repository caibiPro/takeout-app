package com.mingqing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingqing.common.utils.Result;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;

public interface DishService extends IService<Dish> {

	boolean saveWithFlavor(DishDTO dishDTO);

	IPage<DishDTO> getDishWithCategory(int page, int pageSize, String name);

	DishDTO getByIdWithFlavor(Long id);
}
