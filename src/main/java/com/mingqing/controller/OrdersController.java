package com.mingqing.controller;

import com.mingqing.common.utils.Result;
import com.mingqing.entity.Orders;
import com.mingqing.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

  @Autowired
  private OrdersService ordersService;

  @PostMapping("/submit")
  public Result<String> submit(@RequestBody Orders orders) {
    log.info("orders:{}", orders);
    ordersService.submit(orders);
    return Result.success("用户下单成功");
  }
}