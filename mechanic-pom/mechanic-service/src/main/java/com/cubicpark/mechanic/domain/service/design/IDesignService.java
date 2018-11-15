package com.cubicpark.mechanic.domain.service.design;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.design.Design;

public interface IDesignService extends IService<Design> {
    /**
     * 根据查询条件查询信息
     * @param designCode 工单编号可不传
     * @return 查询结果
     */
    Page<Design> selectUserPage(Page page, String designCode);

    /**
     * 根据工单编号查询
     * @param designCode
     * @return
     */
    Design selectByDesignCode(String designCode);

    /**
     * 根据合同编号查询
     * @param contractCode
     * @return
     */
    Design selectByContractCode(String contractCode);
}
