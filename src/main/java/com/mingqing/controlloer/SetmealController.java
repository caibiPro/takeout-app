package com.mingqing.controlloer;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.common.utils.Result;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

	@GetMapping("/page")
	public Result<?> page(int page, int pageSize, String name) {
		Page<SetmealDTO> setmealPages = setmealService.pageWithCategory(page, pageSize, name);
		return Result.success(setmealPages);
	}
}
