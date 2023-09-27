package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "employee")
@Data
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private String name;

	private String username;

	private String password;

	private String phone;

	private String sex;

	private String idNumber;

	private Integer status;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableField(fill = FieldFill.INSERT)
	private Long createUser;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUser;
}