package com.mingqing.dto;

import com.mingqing.entity.Dish;
import com.mingqing.entity.DishFlavor;
import java.util.List;
import lombok.Data;

@Data
public class DishDTO extends Dish {

  private List<DishFlavor> flavors;

  private String categoryName;
}
