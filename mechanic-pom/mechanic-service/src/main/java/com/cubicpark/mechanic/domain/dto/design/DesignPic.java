package com.cubicpark.mechanic.domain.dto.design;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description: 设计工单设计图表
 */
@Data
@TableName("t_design_pic")
public class DesignPic {

    private Integer id;

    /*工单编号*/
    private String designCode;

    /*主设计图*/
    private String indexPic;

    /*附属设计图 ;隔开多图*/
    private String exhibitPic;

    private Date createDate;

    private Date updateDate;


}