package com.cubicpark.mechanic.domain.service.express.impl;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.employee.IEmployeeDAO;
import com.cubicpark.mechanic.dao.express.IExpressDAO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.express.ExpressDTO;
import com.cubicpark.mechanic.domain.service.express.IExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ExpressServiceImpl implements IExpressService {

    @Autowired
    private IExpressDAO expressDAO;

    @Autowired
    private IEmployeeDAO employeeDAO;

    //todo
    public Integer create(String contractCode) {
        //生成code
        String expressCode = MenthaCodeUtil.generateMenthaCode("express", 18);
        ExpressDTO expressDTO = new ExpressDTO();
        expressDTO.setContractCode(contractCode);
        expressDTO.setExpressCode(expressCode);
        expressDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        expressDTO.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        Integer i = expressDAO.insertSelective(expressDTO);
        return i;
    }

    public List<ExpressDTO> getAll() {
        return expressDAO.getAll();
    }

    public ExpressDTO findById(Integer id) {
        return expressDAO.selectByPrimaryKey(id);
    }

    public int updateById(ExpressDTO expressDTO) {
        expressDTO.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        return expressDAO.updateByPrimaryKeySelective(expressDTO);
    }

    //根据条件查询信息
    public List<ExpressDTO> selectByCodeOrStatusOrDate(String expressCode, Integer status, Timestamp createTime, Timestamp endTime) {
        return expressDAO.selectByCodeOrStatusOrDate(expressCode,status,createTime,endTime);
    }

    //查找一个月内的工单
    public List<ExpressDTO> findOneMouseDebugOrder() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        Timestamp oneMouseBefore = Timestamp.valueOf(mon);
        return expressDAO.findOneMouseAfterService(oneMouseBefore);
    }


    /**
     * 根据输入的值查询相似的expressCode
     * @param code input框中输入的值
     * @return
     */
    public List<String> searchExpressCodeLike(String code) {
        return expressDAO.searchExpressCodeLike(code);
    }

    //选择工单
    public int choiceDebugOrder(String employeeCode, Integer id) {
        ExpressDTO expressDTO = expressDAO.selectByPrimaryKey(id);
        EmployeeDTO employee = employeeDAO.getEmployeeByCode(employeeCode);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int result = expressDAO.choiceDebugOrder(employeeCode,employee.getName(),id,expressDTO.getVersion(),now);
        return result;
    }
}
