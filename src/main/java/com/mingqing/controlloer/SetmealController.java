package com.mingqing.controlloer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.common.utils.Result;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.entity.Setmeal;
import com.mingqing.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@PostMapping("/status/{status}")
	public Result<?> alterSaleStatus(@PathVariable int status, @RequestParam List<Long> ids) {
		boolean altered = setmealService.reverseStatus(status, ids);
		if (!altered) {
			return Result.error("更改状态失败");
		}
		return Result.success("更改状态成功");
	}

	@DeleteMapping
	public Result<?> removeSetmeal(@RequestParam List<Long> ids) {
		boolean removed = setmealService.removeSetmeal(ids);
		if (!removed) {
			return Result.error("删除套餐失败");
		}
		return Result.success("删除套餐成功");
	}

	@GetMapping("/list")
	public Result<?> list(Setmeal setmeal) {
		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
		queryWrapper.eq(Setmeal::getStatus, 1);
		queryWrapper.orderByDesc(Setmeal::getUpdateTime);

		List<Setmeal> setmeals = setmealService.list(queryWrapper);
		return Result.success(setmeals);
	}
}
