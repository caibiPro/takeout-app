package com.mingqing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

	void saveWithDish(SetmealDTO setmealDTO);

	Page<SetmealDTO> pageWithCategory(int page, int pageSize, String name);

	boolean reverseStatus(int status, List<Long> ids);

	boolean removeSetmeal(List<Long> ids);
}

