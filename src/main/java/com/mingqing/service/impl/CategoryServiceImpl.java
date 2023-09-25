package com.mingqing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.entity.Category;
import com.mingqing.service.CategoryService;
import com.mingqing.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author mingqing
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2023-09-26 00:55:30
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




