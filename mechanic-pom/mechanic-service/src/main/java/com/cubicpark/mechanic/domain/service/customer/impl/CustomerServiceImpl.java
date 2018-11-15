package com.cubicpark.mechanic.domain.service.customer.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerQueryDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerSeaQueryDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerService")
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ICustomerDAO customerDAO;

    public List<CustomerInfoDTO> queryCustomer(String areaCode, String customerName, String grade, String startDate,
            String endDate, String event, String departmentCode, String employeeCode, String currentEmployeeCode,
            int page) {
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
        CustomerQueryDTO query = this.bulidCustomerQuery(areaCode, customerName, grade, startDate, endDate, event,
                employeeCodeList, startIndex, Constants.COMMON_PAGESIZE);

        return customerDAO.queryCustomer(query);
    }
    
    public int queryCustomerTotal(String areaCode, String customerName, String grade, String startDate, String endDate,
            String event, String departmentCode, String employeeCode, String currentEmployeeCode) {
        // 查询员工编号
        List<String> employeeCodeList = new ArrayList<String>();

        // 如果是上级查询部门下所有员工，查询出该部门下所有员工
        if ("all".equals(employeeCode) && StringUtils.isNotBlank(departmentCode)) {
            List<EmployeeDTO> employeeList = employeeService.getEmployeeByDepartmentCode(departmentCode);

            if (employeeList.size() > 0) {
                for (EmployeeDTO employee : employeeList) {
                    employeeCodeList.add(employee.getEmployeeCode());
                }
            } else {
                return 0;
            }
        } else {
            // 普通员工
            employeeCodeList.add(employeeCode);
        }

        CustomerQueryDTO query = this.bulidCustomerQuery(areaCode, customerName, grade, startDate, endDate, event,
                employeeCodeList, 0L, 0);
        return customerDAO.queryCustomerTotal(query);
    }

    private CustomerQueryDTO bulidCustomerQuery(String areaCode, String customerName, String grade, String startDate,
            String endDate, String event, List<String> employeeList, Long startIndex, int pageSize) {
        CustomerQueryDTO customerQuery = new CustomerQueryDTO();
        customerQuery.setAreaCode(areaCode);
        customerQuery.setCustomerName(customerName);
        customerQuery.setGrade(grade);
        customerQuery.setStartDate(startDate);
        customerQuery.setEndDate(endDate);
        customerQuery.setEvent(event);
        customerQuery.setEmployeeList(employeeList);
        customerQuery.setStartIndex(startIndex);
        customerQuery.setPageSize(pageSize);

        return customerQuery;
    }

    public List<CustomerInfoDTO> getCustomerByEmployeeCode(String employeeCode) {
        if (StringUtils.isBlank(employeeCode)) {
            return null;
        }
        List<String> employeeCodeList = new ArrayList<String>();
        employeeCodeList.add(employeeCode);
        CustomerQueryDTO query = this.bulidCustomerQuery(null, null, null, null, null, null, employeeCodeList, 0L, 0);

        return customerDAO.queryCustomer(query);
    }

    public List<CustomerInfoDTO> queryCustomerSea(String startDate, String endDate, int page) {
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

        startIndex = (page - 1) * Constants.COMMON_PAGESIZE;
        CustomerSeaQueryDTO query = this.bulidCustomerSeaQuery(startDate, endDate, startIndex, Constants.COMMON_PAGESIZE);

        return customerDAO.queryCustomerSea(query);
    }

    public int queryCustomerSeaTotal(String startDate, String endDate) {
        CustomerSeaQueryDTO query = this.bulidCustomerSeaQuery(startDate, endDate, 0L, 0);
        return customerDAO.queryCustomerSeaTotal(query);
    }

    @Override
    public List<CustomerInfoDTO> selectCustomer(String customerName, String employeeCode) {
        return customerDAO.selectCustomer(customerName,employeeCode);
    }

    private CustomerSeaQueryDTO bulidCustomerSeaQuery(String startDate, String endDate, Long startIndex, int pageSize) {
        CustomerSeaQueryDTO customerSeaQuery = new CustomerSeaQueryDTO();
        customerSeaQuery.setStartDate(startDate);
        customerSeaQuery.setEndDate(endDate);
        customerSeaQuery.setStartIndex(startIndex);
        customerSeaQuery.setPageSize(pageSize);

        return customerSeaQuery;
    }
}
