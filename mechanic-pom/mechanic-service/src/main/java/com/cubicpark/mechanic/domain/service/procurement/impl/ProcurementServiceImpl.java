package com.cubicpark.mechanic.domain.service.procurement.impl;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.procurement.IProcurementDAO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProcurementServiceImpl implements IProcurementService {

    @Autowired
    private IProcurementDAO procurementDAO;

    @Override
    public int insert(Integer count, ProductMaterialsDTO productMaterialsDTO) {
        ProcurementDTO procurementDTO = new ProcurementDTO();

        //生成code
        String procurementCode = MenthaCodeUtil.generateMenthaCode("procurement", 18);

        procurementDTO.setProcurementCode(procurementCode);
        procurementDTO.setProductMaterialsId(productMaterialsDTO.getId());
        procurementDTO.setCodeName(productMaterialsDTO.getProductMaterialsStandardCode());
        procurementDTO.setCount(count);
        procurementDTO.setStatus(0);//0为待处理状态
        procurementDTO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        procurementDTO.setMaterial(productMaterialsDTO.getProductMaterials());
        procurementDTO.setName(productMaterialsDTO.getProductMaterialsName());
        procurementDTO.setProductCode(productMaterialsDTO.getProductCode());
        procurementDTO.setQuotedPrice(productMaterialsDTO.getProductPrice());
        procurementDTO.setSupplier(productMaterialsDTO.getSupplier());
        return procurementDAO.insert(procurementDTO);
    }

    @Override
    public List<ProcurementDTO> selectByStatusAndEmployeeCode(Integer status,String employeeCode) {
        return procurementDAO.selectByStatusAndEmployeeCode(status,employeeCode);
    }

    @Override
    public ProcurementDTO selectById(Integer id) {
        return procurementDAO.selectById(id);
    }

    @Override
    public int updateById(ProcurementDTO procurementDTO) {
        return procurementDAO.updateById(procurementDTO);
    }

    @Override
    public List<ProcurementDTO> selectAll() {
        return procurementDAO.selectAll();
    }

    @Override
    public ProcurementDTO selectByProductMaterialsId(Integer id) {
        return procurementDAO.selectByProductMaterialsId(id);
    }

    @Override
    public List<ProcurementDTO> selectByProcurementIdArray(List<Integer> procurementId) {
        return procurementDAO.selectByProcurementIdArray(procurementId);
    }

    @Override
    public List<ProcurementDTO> selectByStatus(Integer status) {
        return procurementDAO.selectByStatus(status);
    }
}
