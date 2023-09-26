package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.entity.DishFlavor;
import com.mingqing.service.DishFlavorService;
import com.mingqing.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author mingqing
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-09-26 17:28:44
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




