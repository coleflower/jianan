package com.cubicpark.mechanic.domain.service.finance.impl;

import com.cubicpark.mechanic.dao.finance.IReimbursementItemDAO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO;
import com.cubicpark.mechanic.domain.service.finance.IReimbursementItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 报销单详情
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class ReimbursementItemServiceImpl implements IReimbursementItemService {

    @Autowired
    private IReimbursementItemDAO reimbursementItemDAO;

    @Override
    public List<ReimbursementItemDTO> selectByApplyNo(String applyNo) {
        return reimbursementItemDAO.selectByApplyNo(applyNo);
    }
}
