package com.cubicpark.mechanic.domain.service.fix.impl;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.contract.IContractDAO;
import com.cubicpark.mechanic.dao.fix.IFixDAO;
import com.cubicpark.mechanic.dao.evaluate.IEvaluateDAO;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import com.cubicpark.mechanic.domain.dto.express.ExpressDTO;
import com.cubicpark.mechanic.domain.service.fix.IFixService;
import com.cubicpark.mechanic.domain.service.evaluate.IEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 调试工单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class FixServiceImpl implements IFixService {

    @Autowired
    private IContractDAO iContractDAO;

    @Autowired
    private IFixDAO debugOrderDAO;

    @Autowired
    private IEvaluateDAO productEvaluateDAO;

    @Autowired
    private IEvaluateService IEvaluateService;

    /**
     * 根据物流发货自动创建一个新调试工单
     * @param expressDTO
     * @return
     */
    public int create(ExpressDTO expressDTO,String handlerCode,String handlerName) {
        FixDTO fixDTO = new FixDTO();
        //生成code
        ContractDTO contractDTO = iContractDAO.getContractByContractCode(expressDTO.getContractCode());
        String debugOrderCode = MenthaCodeUtil.generateMenthaCode("debugorder", 18);
        fixDTO.setHandlerCode(handlerCode);
        fixDTO.setHandlerName(handlerName);
        fixDTO.setDebugOrderCode(debugOrderCode);
        fixDTO.setContractCode(expressDTO.getContractCode());
        fixDTO.setExpressCode(expressDTO.getExpressCode());
        fixDTO.setCustomerCode(contractDTO.getCustomerCode());
        fixDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        fixDTO.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        int result = debugOrderDAO.insertSelective(fixDTO);
        return result;
    }

    /**
     * 根据合同编号查询调试工单
     * @return
     */
    public FixDTO selectByContractCode(String contractCode) {
        FixDTO fixDTO = debugOrderDAO.selectByContractCode(contractCode);
        if(fixDTO == null){
            return null;
        }
        return fixDTO;
    }

    //查找一个月内的工单
    public List<FixDTO> findOneMouseDebugOrder() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        Timestamp oneMouseBefore = Timestamp.valueOf(mon);
        return debugOrderDAO.findOneMouseDebugOrder(oneMouseBefore);
    }

    //查询所有的工单
    public List<FixDTO> findAll() {
        return debugOrderDAO.findAll();
    }

    //根据id查询工单
    public FixDTO findById(Integer id) {
        return debugOrderDAO.selectByPrimaryKey(id);
    }

    //修改工单信息
    public int update(FixDTO record) {
        //如果原来状态为处理中,现在状态为处理完成,则生成评价工单
        //查找旧的工单
        FixDTO old = debugOrderDAO.selectByPrimaryKey(record.getId());
        record.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        int result = debugOrderDAO.updateByPrimaryKeySelective(record);
        int count = productEvaluateDAO.selectByContractCode(record.getContractCode());
        if(count == 0){
            //说明之前没有创建相同的评论工单
            if(old.getStatus() == 1 && record.getStatus() == 2){
                //自动生成评论工单
                record.setContractCode(old.getContractCode());
                IEvaluateService.create(record);
            }
        }
        return result;
    }

    //选择工单
    public int choiceDebugOrder(String employeeCode, Integer id) {
        FixDTO fixDTO = debugOrderDAO.selectByPrimaryKey(id);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int result = debugOrderDAO.choiceDebugOrder(employeeCode,id, fixDTO.getVersion(),now);
        return result;
    }

    //根据条件查询信息
    public List<FixDTO> selectByCodeOrStatusOrDate(String expressCode, Integer status, Timestamp createTime, Timestamp endTime) {
        return debugOrderDAO.selectByCodeOrStatusOrDate(expressCode,status,createTime,endTime);
    }

    /**
     * 根据输入的值查询相似的expressCode
     * @param code input框中输入的值
     * @return
     */
    public List<String> searchExpressCodeLike(String code) {
        return debugOrderDAO.searchDebugOrderCodeLike(code);
    }

    @Override
    public List<FixDTO> selectByEmployeeCodeAndFixTime(String employeeCode) {
        return debugOrderDAO.selectByEmployeeCodeAndFixTime(employeeCode);
    }

}
