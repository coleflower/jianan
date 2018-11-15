/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: IReimbursementItemDAO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:32:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.dao.finance;

import com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IReimbursementItemDAO {

    /**
     * 〈一句话功能简述〉<br>
     * 根据流水号查询报销明细
     *
     * @param [applyNo]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ReimbursementItemDTO> selectByApplyNo(String applyNo);

}
