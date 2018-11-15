package com.cubicpark.mechanic.domain.service.express;

import com.cubicpark.mechanic.domain.dto.express.ExpressDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface IExpressService {

    //生成物流工单
    //TODO
    Integer create(String contractCode);

    //获取所有的物流信息
    List<ExpressDTO> getAll();

    //根据id查询工单
    ExpressDTO findById(Integer id);

    //更新物流工单
    int updateById(ExpressDTO expressDTO);

    //查询一个月内工单
    List<ExpressDTO> findOneMouseDebugOrder();

    //根据条件检索
    List<ExpressDTO> selectByCodeOrStatusOrDate(String expressCode, Integer status, Timestamp createTime, Timestamp endTime);

    //根据输入的值查询相似的expressCode
    List<String> searchExpressCodeLike(String code);

    //改变调试工单状态
    int choiceDebugOrder(String employeeCode,Integer id);
}
