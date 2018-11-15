package com.cubicpark.mechanic.domain.service.finance;

import com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO;

import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 报销详情
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IReimbursementItemService {

    /**
     * 〈一句话功能简述〉<br>
     * 根据流水单号查询
     *
     * @param [applyNo]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ReimbursementItemDTO> selectByApplyNo(String applyNo);
}
