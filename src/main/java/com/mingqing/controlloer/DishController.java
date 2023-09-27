package com.mingqing.controlloer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.mingqing.common.utils.Result;
import com.mingqing.dto.DishDTO;
import com.mingqing.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

	@Autowired
	private DishService dishService;

	@PostMapping
	public Result<?> save(@RequestBody DishDTO dishDTO) {
		boolean saved = dishService.saveWithFlavor(dishDTO);
		if (!saved) {
			return Result.error("新增菜品失败");
		}
		return Result.success("新增菜品成功");
	}

	@GetMapping("/page")
	public Result<?> page(int page, int pageSize, String name) {
		IPage<DishDTO> dishWithCategory = dishService.getDishWithCategory(page, pageSize, name);
		List<?> records = dishWithCategory.getRecords();
		log.info("DishDTO: {}", records);
		return Result.success(dishWithCategory);
	}

	@GetMapping("/{id}")
	public Result<?> getByIdWithFlavor(@PathVariable Long id) {
		DishDTO dishWithFlavor = dishService.getByIdWithFlavor(id);
		return Result.success(dishWithFlavor);
	}

	@PutMapping
	public Result<?> update(@RequestBody DishDTO dishDTO) {
		log.info("接受到的数据为：{}", dishDTO);
		boolean updated = dishService.updateWithFlavor(dishDTO);
		if (!updated) {
			return Result.error("修改菜品失败");
		}
		return Result.success("修改菜品成功");
	}
}
