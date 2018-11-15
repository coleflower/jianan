/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: ReimbursementItemDTO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:11:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto.finance;

import java.sql.Timestamp;

/**
 * 〈一句话功能简述〉<br> 
 * 报销单据明细DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ReimbursementItemDTO {
    // ID
    private int id;
    // 申请流水号
    private String applyNo;
    // 报销金额
    private String cost;
    // 报销类别
    private String category;
    // 费用明细
    private String detail;
    // 票据资料
    private String bill;
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
     * @return the applyNo
     */
    public String getApplyNo() {
        return applyNo;
    }
    /**
     * @param applyNo the applyNo to set
     */
    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }
    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }
    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
    }
    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }
    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
    /**
     * @return the bill
     */
    public String getBill() {
        return bill;
    }
    /**
     * @param bill the bill to set
     */
    public void setBill(String bill) {
        this.bill = bill;
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
