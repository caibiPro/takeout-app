package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@TableName(value = "order_detail")
@Data
public class OrderDetail implements Serializable {

  @TableField(exist = false)
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;

  /**
   * 名字
   */
  private String name;

  /**
   * 图片
   */
  private String image;

  /**
   * 订单id
   */
  private Long orderId;

  /**
   * 菜品id
   */
  private Long dishId;

  /**
   * 套餐id
   */
  private Long setmealId;

  /**
   * 口味
   */
  private String dishFlavor;

  /**
   * 数量
   */
  private Integer number;

  /**
   * 金额
   */
  private BigDecimal amount;
}