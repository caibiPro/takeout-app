package com.mingqing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import com.mingqing.injector.base.CustomBaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DishMapper extends CustomBaseMapper<Dish> {

  IPage<DishDTO> selectWithCategory(IPage<?> page, @Param("name") String name);

  DishDTO selectByIdWithFlavor(Long id);

  List<DishDTO> selectWithFlavors(Dish dish);
}




