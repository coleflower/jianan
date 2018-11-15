package com.cubicpark.mechanic.domain.dto.assessor;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("t_assessor")
public class Assessor {

    private Integer id;

    //采购id
    private  Integer procurementId;

    //部门编号
    private String departmentCode;

    //员工编号
    private String employeeCode;

    //序号
    private Integer sort;

    //类型 0普通审核 1协同审核
    private Integer type;

    //审核状态 0已经审核 1当前审核人审核
    private Integer status;
}
