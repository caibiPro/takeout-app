package com.mingqing.controlloer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.Category;
import com.mingqing.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public Result<?> save(@RequestBody Category category) {
		boolean saved = categoryService.save(category);
		if (!saved) {
			return Result.error("添加失败");
		}
		return Result.success("添加成功");
	}

	@GetMapping("/page")
	public Result<?> page(int page, int pageSize) {
		Page<Category> pageInfo = new Page<>(page, pageSize);
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByAsc(Category::getSort);
		categoryService.page(pageInfo, queryWrapper);
		return Result.success(pageInfo);
	}
}
