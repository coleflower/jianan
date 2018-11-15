package com.cubicpark.mechanic.domain.dto.employee;

import java.sql.Timestamp;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 雇员DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmployeeDTO {
    // ID
    private int id;
    // 雇员编号（工号）
    private String employeeCode;
    // 登录密码
    private String passwords;
    // 所属部门
    private String departmentCode;
    // 职位
    private String positionCode;
    // 姓名
    private String name;
    // 性别
    private String sex;
    // 最高学历
    private String education;
    // 毕业院校
    private String college;
    // 分机号
    private String extension;
    // 联系电话
    private String telephone;
    // 入职时间
    private Timestamp entryDate;
    // 离职时间
    private Timestamp leaveDate;
    // 帐号状态
    private String accountStatus;
    // 交接人
    private String handoverPerson;
    // 角色
    private String roleCode;
    // 菜单权限
    private String permission;
    // 销售区域
    private String salesAreas;
    // 备注
    private String remarks;
    // 创建日期
    private Timestamp createDate;
    // 更新日期
    private Timestamp updateDate;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getPasswords() {
        return passwords;
    }
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getPositionCode() {
        return positionCode;
    }
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Timestamp getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }
    public Timestamp getLeaveDate() {
        return leaveDate;
    }
    public void setLeaveDate(Timestamp leaveDate) {
        this.leaveDate = leaveDate;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    public String getHandoverPerson() {
        return handoverPerson;
    }
    public void setHandoverPerson(String handoverPerson) {
        this.handoverPerson = handoverPerson;
    }
    public String getRoleCode() {
        return roleCode;
    }
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public String getSalesAreas() {
        return salesAreas;
    }
    public void setSalesAreas(String salesAreas) {
        this.salesAreas = salesAreas;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public Timestamp getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
