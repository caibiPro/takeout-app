package com.mingqing.dto;

import com.mingqing.entity.Dish;
import com.mingqing.entity.DishFlavor;
import lombok.Data;

import java.util.List;

@Data
public class DishDTO extends Dish {

	private List<DishFlavor> flavors;

	private String categoryName;
}
