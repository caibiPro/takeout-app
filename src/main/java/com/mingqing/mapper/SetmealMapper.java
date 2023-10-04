package com.mingqing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.entity.Setmeal;

public interface SetmealMapper extends BaseMapper<Setmeal> {

  Page<SetmealDTO> selectWithCategory(Page<SetmealDTO> pageInfo, String name);
}




