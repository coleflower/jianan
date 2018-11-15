package com.cubicpark.mechanic.domain.service.aftersale.impl;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.aftersale.IAfterSaleDAO;
import com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs;
import com.cubicpark.mechanic.domain.service.aftersale.IAfterSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 售后服务Impl
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class AfterSaleServiceImpl implements IAfterSaleService {


    @Autowired
    private IAfterSaleDAO iAfterSaleDAO;

    //新建一个服务工单
    public int create(AfterSaleDTOWithBLOBs record) {

        //生成code
        String afterServiceCode = MenthaCodeUtil.generateMenthaCode("aftersale", 18);

        record.setAfterServiceCode(afterServiceCode);
        record.setCreateDate(new Timestamp(System.currentTimeMillis()));
        record.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        int result = iAfterSaleDAO.insertSelective(record);
        return result;
    }

    //查找一个月内的工单
    public List<AfterSaleDTOWithBLOBs> findOneMouseDebugOrder() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        Timestamp oneMouseBefore = Timestamp.valueOf(mon);
        return iAfterSaleDAO.findOneMouseAfterService(oneMouseBefore);
    }

    //根据id查询工单
    public AfterSaleDTOWithBLOBs findById(Integer id) {
        return iAfterSaleDAO.selectByPrimaryKey(id);
    }

    public int update(AfterSaleDTOWithBLOBs afterServiceDTO) {

        int result = iAfterSaleDAO.updateByPrimaryKeySelective(afterServiceDTO);
        return result;
    }

    public int choiceDebugOrder(String employeeCode, Integer id) {
        AfterSaleDTOWithBLOBs afterServiceDTO = iAfterSaleDAO.selectByPrimaryKey(id);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int result = iAfterSaleDAO.choiceDebugOrder(employeeCode,id,now,afterServiceDTO.getVersion());
        return result;
    }

    @Override
    public int deleteById(Integer id) {
        return iAfterSaleDAO.deleteByPrimaryKey(id);
    }

    //查询所有的工单
    public List<AfterSaleDTOWithBLOBs> findAll() {
        return iAfterSaleDAO.findAll();
    }

    //根据条件查询信息
    public List<AfterSaleDTOWithBLOBs> selectByCodeOrStatusOrDate(String afterServiceCode, Integer status, Timestamp createTime, Timestamp endTime) {
        return iAfterSaleDAO.selectByCodeOrStatusOrDate(afterServiceCode,status,createTime,endTime);
    }

    /**
     * 根据输入的值查询相似的Code
     * @param code input框中输入的值
     * @return
     */
    public List<String> searchCodeLike(String code) {
        return iAfterSaleDAO.searchCodeLike(code);
    }
}
