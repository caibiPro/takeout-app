package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.entity.OrderDetail;
import com.mingqing.mapper.OrderDetailMapper;
import com.mingqing.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
	implements OrderDetailService {

}




