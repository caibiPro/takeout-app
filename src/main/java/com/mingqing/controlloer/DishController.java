package com.mingqing.controlloer;

import com.mingqing.common.utils.Result;
import com.mingqing.dto.DishDTO;
import com.mingqing.service.DishFlavorService;
import com.mingqing.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;

	@PostMapping
	public Result<?> save(@RequestBody DishDTO dishDTO) {
		log.info("菜品及口味接值情况：{}", dishDTO.toString());
		boolean saved = dishService.saveWithFlavor(dishDTO);
		if (!saved) {
			return Result.error("新增菜品失败");
		}
		return Result.success("新增菜品成功");
	}
}
