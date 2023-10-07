package com.mingqing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mingqing.common.utils.Result;
import com.mingqing.dto.DishDTO;
import com.mingqing.entity.Dish;
import com.mingqing.service.DishService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

  @Autowired
  private DishService dishService;

  @Autowired
  private RedisTemplate redisTemplate;

  @PostMapping
  public Result<?> save(@RequestBody DishDTO dishDTO) {
    boolean saved = dishService.saveWithFlavor(dishDTO);
    if (!saved) {
      return Result.error("新增菜品失败");
    }
    String key = "dish_" + dishDTO.getCategoryId() + "_" + dishDTO.getStatus();
    redisTemplate.delete(key);

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

    String key = "dish_" + dishDTO.getCategoryId() + "_" + dishDTO.getStatus();
    redisTemplate.delete(key);

    return Result.success("修改菜品成功");
  }

  @PostMapping("/status/{status}")
  public Result<?> alterStatus(@PathVariable int status, @RequestParam("ids") List<Long> ids) {
    log.info("status = {}", status);
    log.info("ids = {}", ids);
    boolean reversed = dishService.reverseStatus(status, ids);
    if (!reversed) {
      return Result.error("更改状态失败");
    }
    return Result.success("更改状态成功");
  }

  @DeleteMapping
  public Result<?> removeDishes(@RequestParam List<Long> ids) {
    log.info("ids = {}", ids);
    boolean removed = dishService.removeDishes(ids);
    if (!removed) {
      return Result.error("删除菜品失败");
    }
    return Result.success("删除菜品成功");
  }

  @GetMapping("/list")
  public Result<?> list(Dish dish) {
    log.info("dish = {}", dish);
//		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//		queryWrapper.eq(Dish::getStatus, 1);
//		queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//		List<Dish> list = dishService.list(queryWrapper);

    List<DishDTO> list = dishService.listWithFlavors(dish);
    return Result.success(list);
  }
}
