package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "category")
@Data
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("id")
	private Long id;

	private Integer type;

	private String name;

	private Integer sort;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUser;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUser;
}