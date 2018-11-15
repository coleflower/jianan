package com.cubicpark.mechanic.controller.procurement;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementGoodsDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementGoodsService;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementService;
import com.cubicpark.mechanic.domain.service.production.IProductMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
@Component
public class ProcurementGoodsController{

    @Autowired
    private IProcurementService procurementService;

    @Autowired
    private IProductMaterialsService productMaterialsService;

    @Autowired
    private IProcurementGoodsService procurementGoodsService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void run() {
        //查询审核成功的采购单
        //查询审核成功的采购单
        List<ProcurementDTO> procurementDTOList = procurementService.selectByStatus(2);//2为审核成功状态

        if(procurementDTOList == null){
            return;
        }
        List<ProductMaterialsDTO> productMaterialsDTOS = new ArrayList<>();
        int a = 0;
        for(int i = 0;i < procurementDTOList.size();i++){
            ProductMaterialsDTO productMaterials = productMaterialsService.selectById(procurementDTOList.get(i).getProductMaterialsId());
            int count = productMaterials.getProductMaterialsCount();
            a = i;
            for(a = i+1;a < procurementDTOList.size();a++){
                ProductMaterialsDTO productMaterials1 = productMaterialsService.selectById(procurementDTOList.get(a).getProductMaterialsId());
                //是否有相同的原料,相同的为true,不同的为false
                boolean isSame = false;
                //如果名字/材料/编号都一样的话就说明是同一件原料
                if(productMaterials.getProductMaterialsName().equals(productMaterials1.getProductMaterialsName())
                        && productMaterials.getProductMaterials().equals(productMaterials1.getProductMaterials())
                        && productMaterials.getProductMaterialsStandardCode().equals(productMaterials1.getProductMaterialsStandardCode())){
                    //同样的原料数量进行添加
                    count += productMaterials1.getProductMaterialsCount();
                    productMaterials.setProductMaterialsCount(count);
                    //添加到集合中
                    productMaterialsDTOS.add(productMaterials);
                    procurementDTOList.remove(a);
                    isSame = true;
                }else if(a == procurementDTOList.size()-1 && isSame == false){
                    //没有相同的原料直接添加
                    productMaterialsDTOS.add(productMaterials);
                    isSame = false;
                }
            }

        }

        //封装procurementGoodsDTO
        List<ProcurementGoodsDTO> procurementGoodsDTOList = new ArrayList<>();
        //生成今天的code
        String procurementGoodsCode = MenthaCodeUtil.generateMenthaCode("procurementgoods", 18);
        for(ProductMaterialsDTO productMaterialsDTO : productMaterialsDTOS){

            ProcurementGoodsDTO procurementGoodsDTO = new ProcurementGoodsDTO();

            procurementGoodsDTO.setProcurementGoodsCode(procurementGoodsCode);
            procurementGoodsDTO.setName(productMaterialsDTO.getProductMaterialsName());
            procurementGoodsDTO.setMaterial(productMaterialsDTO.getProductMaterials());
            procurementGoodsDTO.setStandardCode(productMaterialsDTO.getProductMaterialsStandardCode());
            procurementGoodsDTO.setCount(productMaterialsDTO.getProductMaterialsCount());
            procurementGoodsDTO.setCreateTime(new Timestamp(System.currentTimeMillis()));

            procurementGoodsDTOList.add(procurementGoodsDTO);
        }

        //批量插入
        procurementGoodsService.insertBatch(procurementGoodsDTOList);

    }

}
