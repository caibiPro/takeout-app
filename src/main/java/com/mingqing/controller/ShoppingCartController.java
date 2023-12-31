package com.mingqing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mingqing.common.utils.BaseContext;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.ShoppingCart;
import com.mingqing.service.ShoppingCartService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @PostMapping("/add")
  public Result<?> addCart(@RequestBody ShoppingCart shoppingCart) {
    log.info(shoppingCart.toString());
    // 设置userID
    Long loginId = BaseContext.getLoginId();
    shoppingCart.setUserId(loginId);

    // 以userId和dishId/setmealId为键搜索购物车中是否已经存在该项
    LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ShoppingCart::getUserId, loginId);
    queryWrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId,
        shoppingCart.getDishId());
    queryWrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId,
        shoppingCart.getSetmealId());

    ShoppingCart cartItem = shoppingCartService.getOne(queryWrapper);
    if (cartItem != null) {
      // 购物车项已存在，直接数量+1
      cartItem.setNumber(cartItem.getNumber() + 1);
      shoppingCartService.updateById(cartItem);
    } else {
      // 购物车项不存在，新插入一项
      shoppingCart.setNumber(1);
      shoppingCartService.save(shoppingCart);
      cartItem = shoppingCart;
    }

    return Result.success(cartItem);
  }

  @GetMapping("/list")
  public Result<?> list() {
    LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getLoginId());
    queryWrapper.orderByDesc(ShoppingCart::getCreateTime);

    List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
    return Result.success(list);
  }

  @DeleteMapping("/clean")
  public Result<?> clean() {
    LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
    Long userId = BaseContext.getLoginId();
    queryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
    shoppingCartService.remove(queryWrapper);
    return Result.success("清空购物车成功");
  }
}


