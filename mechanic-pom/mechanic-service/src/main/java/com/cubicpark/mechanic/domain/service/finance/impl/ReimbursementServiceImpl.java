package com.cubicpark.mechanic.domain.service.finance.impl;

import com.cubicpark.mechanic.dao.finance.IReimbursementDAO;
import com.cubicpark.mechanic.dao.finance.IReimbursementItemDAO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO;
import com.cubicpark.mechanic.domain.service.finance.IReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 报销单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class ReimbursementServiceImpl implements IReimbursementService {

    @Autowired
    private IReimbursementDAO reimbursementDAO;

    @Autowired
    private IReimbursementItemDAO reimbursementItemDAO;

    public List<ReimbursementDTO> findAll(){
        List<ReimbursementDTO> reimbursementDTOList = reimbursementDAO.findAll();
        for(ReimbursementDTO reimbursementDTO : reimbursementDTOList){
            List<ReimbursementItemDTO> reimbursementItemDTOS = reimbursementItemDAO.selectByApplyNo(reimbursementDTO.getApplyNo());
            BigDecimal cost = new BigDecimal(0);
            for(ReimbursementItemDTO reimbursementItemDTO : reimbursementItemDTOS){
                cost = new BigDecimal(reimbursementItemDTO.getCost()).add(cost);
            }
            reimbursementDTO.setCost(cost.toString());
        }
        return reimbursementDTOList;
    }

    @Override
    public ReimbursementDTO findById(Integer id) {
        return reimbursementDAO.findById(id);
    }

    @Override
    public int updateCostById(String cost, Integer id) {
        return reimbursementDAO.updateCostById(cost,id);
    }

    @Override
    public int updateById(Integer id, String state, String payway, String voucher, Date payTime, String remarks, String employeeCode) {
        return reimbursementDAO.updateById(id,state,payway,voucher,payTime,remarks,employeeCode);
    }

    @Override
    public ReimbursementDTO selectByApplyNo(String applyNo) {
        return reimbursementDAO.selectByApplyNo(applyNo);
    }

    @Override
    public int updateByIdRefuse(Integer id, String state, String remarks, String employeeCode) {
        return reimbursementDAO.updateByIdRefuse(id,state,remarks,employeeCode);
    }

    //根据条件查询信息
    public List<ReimbursementDTO> selectByCodeOrStatusOrDate(String expressCode, String status, Timestamp createTime, Timestamp endTime) {
        return reimbursementDAO.selectByCodeOrStatusOrDate(expressCode,status,createTime,endTime);
    }

    //选择工单
    public int choiceDebugOrder(String employeeCode, Integer id) {
        ReimbursementDTO reimbursementDTO = reimbursementDAO.findById(id);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int result = reimbursementDAO.choiceDebugOrder(employeeCode,id, reimbursementDTO.getVersion(),now);
        return result;
    }

    /**
     * 根据输入的值查询相似的expressCode
     * @param code input框中输入的值
     * @return
     */
    public List<String> searchExpressCodeLike(String code) {
        return reimbursementDAO.searchExpressCodeLike(code);
    }

}
