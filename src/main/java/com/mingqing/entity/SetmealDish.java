package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName(value = "setmeal_dish")
@Data
public class SetmealDish implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("id")
	private Long id;

	private Long setmealId;

	private Long dishId;

	private String name;

	private BigDecimal price;

	private Integer copies;

	private Integer sort;

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