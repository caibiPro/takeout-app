package com.mingqing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mingqing.entity.Setmeal;
import com.mingqing.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDTO extends Setmeal {

	@JsonProperty("setmealDishes")
	private List<SetmealDish> setmealDishList;

}
