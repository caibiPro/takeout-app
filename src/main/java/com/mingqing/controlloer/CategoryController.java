package com.mingqing.controlloer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.Category;
import com.mingqing.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@DeleteMapping
	public Result<?> delete(Long id) {
		categoryService.removeById(id);
		return Result.success("删除成功");
	}

	@PutMapping
	public Result<?> update(@RequestBody Category category) {
		log.info("修改的分类信息未：{}", category);
		boolean update = categoryService.updateById(category);
		if (!update) {
			return Result.error("修改分类信息失败");
		}
		return Result.success("修改分类信息成功");
	}

	@GetMapping("/list")
	public Result<?> list(Category category) {
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
		queryWrapper.orderByDesc(Category::getSort).orderByAsc(Category::getUpdateTime);
		List<Category> list = categoryService.list(queryWrapper);

		return Result.success(list);
	}
}
