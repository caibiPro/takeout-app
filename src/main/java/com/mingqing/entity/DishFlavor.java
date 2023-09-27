package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "dish_flavor")
@Data
public class DishFlavor implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("id")
	private Long id;

	private Long dishId;

	private String name;

	private String value;

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