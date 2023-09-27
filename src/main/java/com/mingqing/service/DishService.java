package com.mingqing.service;

import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {

	boolean saveWithFlavor(DishDTO dishDTO);
}
