/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripPlanServiceImpl.java
 * Author:   first.ji
 * Date:     2018年9月12日 下午2:32:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.service.trip.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.trip.ITripPlanDAO;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripItemDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripPlanDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripPlanQueryDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.trip.ITripItemService;
import com.cubicpark.mechanic.domain.service.trip.ITripPlanService;
import com.cubicpark.mechanic.exception.ServiceException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class TripPlanServiceImpl implements ITripPlanService {

    @Autowired
    private ITripPlanDAO tripPlanDAO;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ITripItemService tripItemService;

    public String addTrip(TripDTO trip) {
        Assert.notNull(trip, "trip must be not null");

        tripPlanDAO.addTripPlan(trip.getTripPlan());
        tripItemService.addTripItem(trip.getTripItems());
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String addTripPlan(TripPlanDTO tripPlan) {
        tripPlanDAO.addTripPlan(tripPlan);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String cancelTripPlan(String planCode, String employeeCode) {
        // 当前用户且出差计划状态为带审批才可以进行作废
        tripPlanDAO.modifyTripPlanState(planCode, employeeCode, Constants.TRIP_STATE_TO, Constants.TRIP_STATE_CANCEL,
                null);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String changeTripState(String planCode, String oldState, String newState, String approver) {
        tripPlanDAO.modifyTripPlanState(planCode, null, oldState, newState, approver);
        return CommonErrorCode.SUCCESS.getValue();
    }
    
    public int queryTripPlanTotal(String startDate, String endDate, String state, String departmentCode,
            String employeeCode) {
        // 查询员工编号
        List<String> employeeCodeList = new ArrayList<String>();
        // 如果是上级查询部门下所有员工，查询出该部门下所有员工
        if ("all".equals(employeeCode) && StringUtils.isNotBlank(departmentCode)) {
            List<EmployeeDTO> employeeList = employeeService.getEmployeeByDepartmentCode(departmentCode);

            for (EmployeeDTO employee : employeeList) {
                employeeCodeList.add(employee.getEmployeeCode());
            }
        } else {
            // 普通员工
            employeeCodeList.add(employeeCode);
        }
        
        TripPlanQueryDTO query = this.buildTripPlanQuery(startDate, endDate, state, employeeCodeList, 0L, 0);
        return tripPlanDAO.queryTripPlanTotal(query);
    }


    public List<TripPlanDTO> queryTripPlan(String startDate, String endDate, String state, String departmentCode,
            String employeeCode, int page) {
        // 分页查询初始数
        long startIndex = 0L;
        // 查询员工编号
        List<String> employeeCodeList = new ArrayList<String>();

        // 查询日期需成对出现
        if (StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)) {
            if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
                throw new ServiceException("查询日期需要成对出现！");
            }
        }

        // 如果是上级查询部门下所有员工，查询出该部门下所有员工
        if ("all".equals(employeeCode) && StringUtils.isNotBlank(departmentCode)) {
            List<EmployeeDTO> employeeList = employeeService.getEmployeeByDepartmentCode(departmentCode);

            for (EmployeeDTO employee : employeeList) {
                employeeCodeList.add(employee.getEmployeeCode());
            }
        } else {
            // 普通员工
            employeeCodeList.add(employeeCode);
        }

        startIndex = (page - 1) * Constants.COMMON_PAGESIZE;
        TripPlanQueryDTO query = this.buildTripPlanQuery(startDate, endDate, state, employeeCodeList, startIndex,
                Constants.COMMON_PAGESIZE);
        
        return tripPlanDAO.queryTripPlan(query);
    }

    public TripPlanDTO getTripPlan(String planCode) {
        return tripPlanDAO.getTripPlan(planCode);
    }

    public List<TripDTO> getToApprovalTripPlan() {
        List<TripDTO> trips = new ArrayList<TripDTO>();

        // 获取所有待审批的出差计划
        List<TripPlanDTO> tripPlans = tripPlanDAO.getTripPlanByState(Constants.TRIP_STATE_TO);
        for (TripPlanDTO tripPlan : tripPlans) {
            TripDTO trip = new TripDTO();
            // 获取出差人员姓名，部门，职位
            EmployeeDTO employee = employeeService.getEmployeeByCode(tripPlan.getEmployeeCode());
            DepartmentDTO department = departmentService.getDepartmentByCode(employee.getDepartmentCode());
            PositionDTO postion = positionService.getPositionByCode(employee.getPositionCode());

            // 获取出差行程明细
            List<TripItemDTO> tripItems = tripItemService.getTripItemByPlanCode(tripPlan.getPlanCode());

            // 组装完整的差旅计划
            trip.setName(employee.getName());
            trip.setDepartmentName(department.getDepartmentName());
            trip.setPositionName(postion.getPositionName());
            trip.setTripPlan(tripPlan);
            trip.setTripItems(tripItems);

            trips.add(trip);
        }

        return trips;
    }

    private TripPlanQueryDTO buildTripPlanQuery(String startDate, String endDate, String state,
            List<String> employeeList, Long startIndex, int pageSize) {
        TripPlanQueryDTO query = new TripPlanQueryDTO();
        query.setState(state);
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setEmployeeList(employeeList);
        query.setStartIndex(startIndex);
        query.setPageSize(pageSize);
        return query;
    }
}
