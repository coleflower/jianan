package com.cubicpark.mechanic.domain.dto.assessor;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("t_assessor_primary")
@Data
public class AssessorPrimaryDTO {

    private Integer id;

    //审核流水号
    private String assessorCode;

    //工单类型
    private String codeType;

    //审核人编号
    private String approverCode;

    //审核人姓名
    private String approverName;

    //排序
    private Integer sort;

    //状态 0审核成功 1待完成审核 null待审核
    private Integer status;

    //备注
    private String remark;

    //审核时间
    private Timestamp approverTime;

    //创建时间
    private Timestamp createTime;
}
