package com.mingqing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.common.exception.CustomRelevanceException;
import com.mingqing.entity.Category;
import com.mingqing.entity.Dish;
import com.mingqing.entity.Setmeal;
import com.mingqing.service.CategoryService;
import com.mingqing.mapper.CategoryMapper;
import com.mingqing.service.DishService;
import com.mingqing.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
	@Autowired
	private DishService dishService;

	@Autowired
	private SetmealService setmealService;

	@Override
	public boolean removeById(Serializable id) {
		// 判断菜品表中是否关联了待删除套餐分类id
		LambdaQueryWrapper<Dish> queryWrapperDish = new LambdaQueryWrapper<>();
		queryWrapperDish.eq(Dish::getCategoryId, id);
		int countInDish = dishService.count(queryWrapperDish);
		if (countInDish > 0) {
			throw new CustomRelevanceException("当前分类下关联了菜品，不能删除");
		}
		// 判断套餐表中是否关联了待删除套餐分类id
		LambdaQueryWrapper<Setmeal> queryWrapperSetmeal = new LambdaQueryWrapper<>();
		queryWrapperSetmeal.eq(Setmeal::getCategoryId, id);
		int countInSetmeal = setmealService.count(queryWrapperSetmeal);

		if (countInSetmeal > 0) {
			throw new CustomRelevanceException("当前分类下关联了套餐，不能删除");
		}
		return super.removeById(id);
	}
}




