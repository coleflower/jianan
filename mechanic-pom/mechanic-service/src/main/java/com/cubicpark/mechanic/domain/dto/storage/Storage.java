package com.cubicpark.mechanic.domain.dto.storage;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.SqlCondition;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description: 仓库表
 */
@Data
@TableName("t_storage")
public class Storage {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String storageCode;

    @TableField(condition = SqlCondition.LIKE)
    private String storageName;

    /*仓库管理员*/
    private String manager;

    @TableField("createdate")
    private Timestamp createDate;

    @TableField("updatedate")
    private Timestamp updateDate;

}