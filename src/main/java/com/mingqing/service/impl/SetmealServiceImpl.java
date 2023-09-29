package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.entity.Setmeal;
import com.mingqing.entity.SetmealDish;
import com.mingqing.mapper.SetmealMapper;
import com.mingqing.service.SetmealDishService;
import com.mingqing.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

	@Autowired
	private SetmealMapper setmealMapper;

	@Autowired
	private SetmealService setmealService;

	@Autowired
	private SetmealDishService setmealDishService;

	@Override
	@Transactional
	public void saveWithDish(SetmealDTO setmealDTO) {
		setmealService.save(setmealDTO);
		Long setmealId = setmealDTO.getId();

		List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishList();
		setmealDishes.stream().peek(item -> item.setSetmealId(setmealId)).collect(Collectors.toList());

		setmealDishService.saveBatch(setmealDishes);
	}

	@Override
	public Page<SetmealDTO> pageWithCategory(int page, int pageSize, String name) {
		Page<SetmealDTO> pageInfo = new Page<>(page, pageSize);
		setmealMapper.selectWithCategory(pageInfo, name);
		return pageInfo;
	}
}




