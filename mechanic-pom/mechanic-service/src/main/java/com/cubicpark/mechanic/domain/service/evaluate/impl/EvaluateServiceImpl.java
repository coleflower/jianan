package com.cubicpark.mechanic.domain.service.evaluate.impl;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.evaluate.IEvaluateDAO;
import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO;
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
 * 评价服务
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class EvaluateServiceImpl implements IEvaluateService {

    @Autowired
    private IEvaluateDAO productEvaluateDAO;

    //调试完成自动生成评价工单
    public int create(FixDTO fixDTO) {

        //生成code
        String productEvaluateCode = MenthaCodeUtil.generateMenthaCode("evaluate", 18);
        EvaluateDTO productEvaluate = new EvaluateDTO();
        productEvaluate.setContractCode(fixDTO.getContractCode());
        productEvaluate.setProductEvluateCode(productEvaluateCode);
        productEvaluate.setCreateDate(new Timestamp(System.currentTimeMillis()));
        productEvaluate.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        int result = productEvaluateDAO.insertSelective(productEvaluate);
        return result;
    }

    //查询一个月内的工单
    @Override
    public List<EvaluateDTO> selectOneMouthOrder() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        Timestamp oneMouseBefore = Timestamp.valueOf(mon);
        return productEvaluateDAO.selectOneMouthOrder(oneMouseBefore);
    }

    @Override
    public EvaluateDTO selectById(Integer id) {
        return productEvaluateDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(EvaluateDTO evaluateDTO) {
        int insert = productEvaluateDAO.updateByPrimaryKey(evaluateDTO);
        return insert;
    }

    //根据条件查询信息
    public List<EvaluateDTO> selectByCodeOrStatusOrDate(String expressCode, Integer status, Timestamp createTime, Timestamp endTime) {
        return productEvaluateDAO.selectByCodeOrStatusOrDate(expressCode,status,createTime,endTime);
    }

    /**
     * 根据输入的值查询相似的expressCode
     * @param code input框中输入的值
     * @return
     */
    public List<String> searchExpressCodeLike(String code) {
        return productEvaluateDAO.searchDebugOrderCodeLike(code);
    }
}
