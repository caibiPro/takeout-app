package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@TableName(value ="dish_flavor")
@Data
public class DishFlavor implements Serializable {
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

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "DishFlavor{" +
            "id=" + id +
            ", dishId=" + dishId +
            ", name='" + name + '\'' +
            ", value='" + value + '\'' +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
            ", isDeleted=" + isDeleted +
            '}';
    }
}