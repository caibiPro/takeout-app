package com.mingqing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mingqing.entity.Setmeal;
import com.mingqing.entity.SetmealDish;
import java.util.List;
import lombok.Data;

@Data
public class SetmealDTO extends Setmeal {

  @JsonProperty("setmealDishes")
  private List<SetmealDish> setmealDishList;

  private String categoryName;

}
