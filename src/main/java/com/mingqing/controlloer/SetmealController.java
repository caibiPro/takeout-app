package com.mingqing.controlloer;

import com.mingqing.common.utils.Result;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Autowired
	private SetmealService setmealService;

	@PostMapping
	public Result<?> save(@RequestBody SetmealDTO setmealDTO) {
		setmealService.saveWithDish(setmealDTO);
		return Result.success("新增套餐成功");
	}
}
