package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@TableName(value = "setmeal")
@Data
@ApiModel("套餐")
public class Setmeal implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId("id")
  @ApiModelProperty("套餐id")
  private Long id;

  private Long categoryId;

  private String name;

  private BigDecimal price;

  private Integer status;

  private String code;

  private String description;

  private String image;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

  @TableField(fill = FieldFill.INSERT)
  private Long createUser;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;

  @TableLogic
  private Integer isDeleted;
}