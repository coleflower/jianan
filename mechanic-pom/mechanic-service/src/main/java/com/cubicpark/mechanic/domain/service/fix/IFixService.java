package com.cubicpark.mechanic.domain.service.fix;

import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import com.cubicpark.mechanic.domain.dto.express.ExpressDTO;

import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 调试订单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IFixService {

    /**
     * 〈一句话功能简述〉<br>
     * 根据物流发货自动创建一个新调试工单
     *
     * @param [expressDTO]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int create(ExpressDTO expressDTO,String handlerCode,String handlerName);

    /**
     * 〈一句话功能简述〉<br>
     * 根据合同编号查询调试工单
     *
     * @param [contractCode]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    FixDTO selectByContractCode(String contractCode);


    /**
     * 〈一句话功能简述〉<br>
     * //查询一个月内工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> findOneMouseDebugOrder();


    /**
     * 〈一句话功能简述〉<br>
     *  //查询所有的工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> findAll();


    /**
     * 〈一句话功能简述〉<br>
     * //根据id查询工单
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.fix.FixDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    FixDTO findById(Integer id);


    /**
     * 〈一句话功能简述〉<br>
     *  //修改信息
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int update(FixDTO record);


    /**
     * 〈一句话功能简述〉<br>
     * //改变调试工单状态
     *
     * @param [employeeCode, id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int choiceDebugOrder(String employeeCode,Integer id);


    /**
     * 〈一句话功能简述〉<br>
     * //根据条件查询信息
     *
     * @param [expressCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> selectByCodeOrStatusOrDate(String expressCode, Integer status, Timestamp createTime, Timestamp endTime);

    /**
     * 根据输入的值查询相似的expressCode
     * @param code input框中输入的值
     * @return
     */
    List<String> searchExpressCodeLike(String code);

    /**
     * 〈一句话功能简述〉<br>
     * 查询需要添加调试时间的调试工单
     *
     * @param employeeCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> selectByEmployeeCodeAndFixTime(String employeeCode);
}
