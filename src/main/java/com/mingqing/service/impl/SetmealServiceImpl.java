package com.mingqing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingqing.common.exception.CustomException;
import com.mingqing.dto.SetmealDTO;
import com.mingqing.entity.Setmeal;
import com.mingqing.entity.SetmealDish;
import com.mingqing.mapper.SetmealMapper;
import com.mingqing.service.SetmealDishService;
import com.mingqing.service.SetmealService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements
    SetmealService {

  @Autowired
  private SetmealMapper setmealMapper;

  @Autowired
  private SetmealService setmealService;

  @Autowired
  private SetmealDishService setmealDishService;

  @Override
  @Transactional
  public void saveWithDish(SetmealDTO setmealDTO) {
    setmealService.save(setmealDTO);
    Long setmealId = setmealDTO.getId();

    List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishList();
    setmealDishes.stream().peek(item -> item.setSetmealId(setmealId)).collect(Collectors.toList());

    setmealDishService.saveBatch(setmealDishes);
  }

  @Override
  public Page<SetmealDTO> pageWithCategory(int page, int pageSize, String name) {
    Page<SetmealDTO> pageInfo = new Page<>(page, pageSize);
    setmealMapper.selectWithCategory(pageInfo, name);
    return pageInfo;
  }

  @Override
  @Transactional
  public boolean reverseStatus(int status, List<Long> ids) {
    List<Setmeal> setmeals = new ArrayList<>();
    for (Long id : ids) {
      Setmeal setmeal = new Setmeal();
      setmeal.setStatus(status);
      setmeal.setId(id);
      setmeals.add(setmeal);
    }
    return updateBatchById(setmeals);
  }

  @Override
  @Transactional
  public boolean removeSetmeal(List<Long> ids) {
    // 候选套餐是否存在在售状态
    LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
    int count = setmealService.count(queryWrapper);
    if (count > 0) {
      throw new CustomException("存在待售套餐，无法删除");
    }

    // 在setmeal表中逻辑删除相关套餐
    LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.in(Setmeal::getId, ids).set(Setmeal::getIsDeleted, 1);
    boolean setmealUpdated = update(new Setmeal(), updateWrapper);

    // 在setmeal_dish表中删除对应套餐的菜品
    LambdaUpdateWrapper<SetmealDish> updateWrapperWithDish = new LambdaUpdateWrapper<>();
    updateWrapperWithDish.in(SetmealDish::getSetmealId, ids).set(SetmealDish::getIsDeleted, 1);
    boolean setmealDishUpdated = setmealDishService.update(new SetmealDish(),
        updateWrapperWithDish);

    return setmealUpdated && setmealDishUpdated;
  }
}




