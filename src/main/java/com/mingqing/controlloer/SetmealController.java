package com.mingqing.controlloer;

import com.mingqing.service.SetmealDishService;
import com.mingqing.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Autowired
	private SetmealService setmealService;

	@Autowired
	private SetmealDishService setmealDishService;

}
