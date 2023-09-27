package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import com.mingqing.entity.DishFlavor;
import com.mingqing.service.DishFlavorService;
import com.mingqing.service.DishService;
import com.mingqing.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

	@Autowired
	private DishFlavorService dishFlavorService;

	@Override
	@Transactional
	public boolean saveWithFlavor(DishDTO dishDTO) {
		// 菜品数据存储到dish表
		boolean savedDish = this.save(dishDTO);
		// 构造DishFlavor对象
		Long dishId = dishDTO.getId();
		List<DishFlavor> flavors = dishDTO.getFlavors();
		flavors = flavors.stream().peek(flavor -> flavor.setDishId(dishId)).collect(Collectors.toList());
		// flavors批量插入存储到dish_flavor表
		boolean savedDishFlavor = dishFlavorService.saveBatch(flavors);

		return savedDish && savedDishFlavor;
	}
}




