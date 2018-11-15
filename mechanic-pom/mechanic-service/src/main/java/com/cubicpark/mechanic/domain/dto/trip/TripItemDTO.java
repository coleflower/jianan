/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripItemDTO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:01:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto.trip;

import java.sql.Timestamp;

/**
 * 〈一句话功能简述〉<br> 
 * 行程安排DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TripItemDTO {
    // ID
    private int id;
    // 计划编号
    private String planCode;
    // 出差地点
    private String destination;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
    // 备注
    private String remarks;
    // 创建日期
    private Timestamp createDate;
    // 更新日期
    private Timestamp updateDate;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the planCode
     */
    public String getPlanCode() {
        return planCode;
    }
    /**
     * @param planCode the planCode to set
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }
    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }
    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }
    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /**
     * @return the updateDate
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }
    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
