package com.mingqing.controlloer;

import com.mingqing.common.utils.Result;
import com.mingqing.entity.Category;
import com.mingqing.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
