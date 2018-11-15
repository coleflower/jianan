package com.cubicpark.mechanic.domain.service.bill.impl;

import com.cubicpark.mechanic.dao.contract.IContractDAO;
import com.cubicpark.mechanic.dao.finance.IFinanceBillDao;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO;
import com.cubicpark.mechanic.domain.service.bill.IFinanceBillService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 开票服务
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class FinanceBillServiceImpl implements IFinanceBillService {

    @Autowired
    private IContractDAO contractDAO;

    @Autowired
    private IFinanceBillDao financeBillDao;
    @Override
    public String[] selectContractByEmployeeCode(String employeeCode) {
        return contractDAO.selectContractCodeByEmployeeCode(employeeCode);
    }

    @Override
    public int insert(FinanceBillDTO financeBillDTO) {
        ContractDTO contractDTO = contractDAO.getContractByContractCode(financeBillDTO.getContractCode());
        financeBillDTO.setCustomerCode(contractDTO.getCustomerCode());
        financeBillDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        financeBillDTO.setStatus("待开票");
        int insert = financeBillDao.insert(financeBillDTO);
        return insert;
    }

    @Override
    public List<FinanceBillDTO> selectAll() {
        return financeBillDao.selectAll();
    }

    @Override
    public List<FinanceBillDTO> selectByContractCode(String contractCode) {
        return financeBillDao.selectByContractCode(contractCode);
    }

    @Override
    public List<FinanceBillDTO> selectUnprocessed() {
        return financeBillDao.selectUnprocessed();
    }

    @Override
    public FinanceBillDTO selectById(Integer id) {
        return financeBillDao.selectById(id);
    }

    @Override
    public int updateById(FinanceBillDTO financeBillDTO) {
        int result = financeBillDao.updateById(financeBillDTO);
        return result;
    }

    @Override
    public List<FinanceBillDTO> selectInOneMouth(String proposer){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        Timestamp oneMouthBefore = Timestamp.valueOf(mon);
        return financeBillDao.findInOneMouth(oneMouthBefore,proposer);
    }

    @Override
    public List<FinanceBillDTO> findByProposer(String proposer) {
        return financeBillDao.findByProposer(proposer);
    }

    /**
     * 根据输入的值查询相似的Code
     * @param code input框中输入的值
     * @return
     */
    public List<String> searchCodeLike(String code) {
        return financeBillDao.searchCodeLike(code);
    }

    //根据条件查询信息
    public List<FinanceBillDTO> selectByCodeOrStatusOrDate(String code, Integer status, Timestamp createTime, Timestamp endTime) {
        return financeBillDao.selectByCodeOrStatusOrDate(code,status,createTime,endTime);
    }

    @Override
    public List<FinanceBillDTO> selectByContractCodeAndStatus(String contractCode, String status) {
        return financeBillDao.selectByContractCodeAndStatus(contractCode,status);
    }

    @Override
    public List<FinanceBillDTO> selectByDepartmentCodeOrEmployeeCodeOrStatusOrDate(String employeeCode, String status, Timestamp startTime, Timestamp endTime) {
        return financeBillDao.selectByDepartmentCodeOrEmployeeCodeOrStatusOrDate(employeeCode,status,startTime,endTime);
    }

}
