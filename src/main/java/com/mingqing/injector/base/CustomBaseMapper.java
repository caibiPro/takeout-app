package com.mingqing.injector.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomBaseMapper<T> extends BaseMapper<T> {

  /**
   * 以下定义的是内置的选装件
   */
  int deleteByIdWithFill(T entity);

  /**
   * 以下为自定义 如果要自动填充，@{@code Param}(xx) xx参数名必须是 list/collection/array 3个的其中之一
   *
   * @param batchList
   * @return
   */
  int mysqlInsertAllBatch(@Param("list") List<T> batchList);

  /**
   * 无论是否存在逻辑删除位都直接删除
   */
  int realDelete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

}
