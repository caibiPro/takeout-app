package com.mingqing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import com.mingqing.entity.DishFlavor;
import com.mingqing.mapper.DishMapper;
import com.mingqing.service.DishFlavorService;
import com.mingqing.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	@Autowired
	private DishFlavorService dishFlavorService;

	@Autowired
	private DishMapper dishMapper;

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

	@Override
	public IPage<DishDTO> getDishWithCategory(int page, int pageSize, String name) {
		Page<DishDTO> pageInfo = new Page<>(page, pageSize);
		return dishMapper.selectWithCategory(pageInfo, name);
	}

	@Override
	public DishDTO getByIdWithFlavor(Long id) {
		return dishMapper.selectByIdWithFlavor(id);
	}

	@Override
	@Transactional
	public boolean updateWithFlavor(DishDTO dishDTO) {
		Long dishId = dishDTO.getId();
		List<DishFlavor> flavors = dishDTO.getFlavors();
		updateById(dishDTO);

		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
		dishFlavorService.remove(queryWrapper);

		flavors = flavors.stream().peek(item -> {
			item.setDishId(dishId);
			item.setId(null);
		}).collect(Collectors.toList());
		dishFlavorService.saveBatch(flavors);
		return true;
	}

	@Override
	public boolean reverseStatus(int status, List<Long> ids) {
		List<Dish> dishList = new ArrayList<>();
		for (Long id : ids) {
			Dish dish = new Dish();
			dish.setId(id);
			dish.setStatus(status);
			dishList.add(dish);
		}
		return updateBatchById(dishList);
	}

	@Override
	@Transactional
	public boolean removeDishes(List<Long> ids) {
		// 在dish表中批量删除
		boolean dishRemoved = removeByIds(ids);

		// 在dish_flavor表中批量删除
		LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(DishFlavor::getDishId, ids);
		boolean flavorRemoved = dishFlavorService.remove(queryWrapper);

		return dishRemoved && flavorRemoved;
	}
}




