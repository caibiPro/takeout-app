package com.mingqing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.common.exception.CustomException;
import com.mingqing.common.utils.BaseContext;
import com.mingqing.entity.AddressBook;
import com.mingqing.entity.OrderDetail;
import com.mingqing.entity.Orders;
import com.mingqing.entity.ShoppingCart;
import com.mingqing.entity.User;
import com.mingqing.mapper.OrdersMapper;
import com.mingqing.service.AddressBookService;
import com.mingqing.service.OrderDetailService;
import com.mingqing.service.OrdersService;
import com.mingqing.service.ShoppingCartService;
import com.mingqing.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @Autowired
  private OrderDetailService orderDetailService;

  @Autowired
  private AddressBookService addressBookService;

  @Autowired
  private UserService userService;

  @Override
  @Transactional
  public void submit(Orders orders) {
    Long userId = BaseContext.getLoginId();

    // 购物车信息
    LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
    List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
    if (shoppingCarts == null) {
      throw new CustomException("购物车为空，无法下单");
    }

    // 地址信息
    Long addressBookId = orders.getAddressBookId();
    AddressBook address = addressBookService.getById(addressBookId);
    if (address == null) {
      throw new CustomException("地址信息有误，无法下单");
    }

    // 用户信息
    User user = userService.getById(userId);

    // 其他
    long id = IdWorker.getId();
    AtomicInteger amount = new AtomicInteger(0);

    // order_detail表中插入订单详情记录
    List<OrderDetail> orderDetails = shoppingCarts.stream().map(cartItem -> {
      OrderDetail orderDetail = new OrderDetail();

      orderDetail.setOrderId(id);
      orderDetail.setName(cartItem.getName());
      orderDetail.setImage(cartItem.getImage());
      orderDetail.setDishId(cartItem.getDishId());
      orderDetail.setSetmealId(cartItem.getSetmealId());
      orderDetail.setDishFlavor(cartItem.getDishFlavor());
      orderDetail.setNumber(cartItem.getNumber());
      orderDetail.setAmount(cartItem.getAmount());
      amount.addAndGet(
          cartItem.getAmount().multiply(new BigDecimal(cartItem.getNumber())).intValue());
      return orderDetail;
    }).collect(Collectors.toList());
    orderDetailService.saveBatch(orderDetails);

    // orders表中插入订单记录
    Orders order = new Orders();
    order.setId(id);
    order.setNumber(String.valueOf(id));
    order.setStatus(2);
    order.setUserId(userId);
    order.setAddressBookId(addressBookId);
    order.setPayMethod(orders.getPayMethod());
    order.setAmount(new BigDecimal(amount.get()));
    order.setRemark(orders.getRemark());
    order.setPhone(address.getPhone());
    order.setAddress((address.getProvinceName() == null ? "" : address.getProvinceName()) + (
        address.getCityName() == null ? "" : address.getCityName()) + (
        address.getDistrictName() == null ? "" : address.getDistrictName()) + (
        address.getDetail() == null ? "" : address.getDetail()));
    order.setUserName(user.getName());
    order.setConsignee(address.getConsignee());
    save(order);

    // 清空购物车
    shoppingCartService.remove(queryWrapper);
  }
}




