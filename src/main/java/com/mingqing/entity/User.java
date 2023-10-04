package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@TableName(value = "user")
@Data
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId("id")
  private Long id;

  private String name;

  private String phone;

  private String sex;

  private String idNumber;

  private String avatar;

  private Integer status;
}