package com.cubicpark.mechanic.domain.service.finance;

import com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO;

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
public interface IReimbursementService {

    /**
     * 〈一句话功能简述〉<br>
     * //查找所有的报销单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ReimbursementDTO> findAll();

    /**
     * 〈一句话功能简述〉<br>
     * //根据id查找
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ReimbursementDTO findById(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * //根据id更改金额大小
     *
     * @param [cost, id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateCostById(String cost,Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * //报销成功
     *
     * @param [id, state, payway, voucher, payTime, remarks, employeeCode]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateById(Integer id, String state, String payway, String voucher, Date payTime, String remarks, String employeeCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据applyNo查询
     *
     * @param [applyNo]
     * @return com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ReimbursementDTO selectByApplyNo(String applyNo);

    /**
     * 〈一句话功能简述〉<br>
     * //报销拒绝
     *
     * @param [id, state, remarks, employeeCode]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByIdRefuse( Integer id, String state, String remarks, String employeeCode);


    /**
     * 〈一句话功能简述〉<br>
     * //根据条件查询信息
     *
     * @param [expressCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ReimbursementDTO> selectByCodeOrStatusOrDate(String expressCode, String status, Timestamp createTime, Timestamp endTime);


    /**
     * 〈一句话功能简述〉<br>
     *  //根据输入的值查询相似的expressCode
     *
     * @param [code]
     * @return java.util.List<java.lang.String>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> searchExpressCodeLike(String code);

    /**
     * 〈一句话功能简述〉<br>
     * 选择调试工单
     *
     * @param [employeeCode, id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int choiceDebugOrder(String employeeCode, Integer id);
}
