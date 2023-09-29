package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.entity.User;
import com.mingqing.mapper.UserMapper;
import com.mingqing.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




