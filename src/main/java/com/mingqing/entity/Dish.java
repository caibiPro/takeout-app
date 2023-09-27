package com.mingqing.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@TableName(value ="dish")
@Data
public class Dish implements Serializable {
    @TableId("id")
    private Long id;

    private String name;

    private Long categoryId;

    private BigDecimal price;

    private String code;

    private String image;

    private String description;

    private Integer status;

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

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Dish{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", categoryId=" + categoryId +
            ", price=" + price +
            ", code='" + code + '\'' +
            ", image='" + image + '\'' +
            ", description='" + description + '\'' +
            ", status=" + status +
            ", sort=" + sort +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
            ", isDeleted=" + isDeleted +
            '}';
    }
}