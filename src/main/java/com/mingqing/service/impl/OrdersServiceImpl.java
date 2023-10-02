package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.entity.Orders;
import com.mingqing.mapper.OrdersMapper;
import com.mingqing.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}




