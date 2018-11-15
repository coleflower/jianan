package com.cubicpark.mechanic.controller.production;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.design.Design;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.production.CheckProductDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductionDTO;
import com.cubicpark.mechanic.domain.service.design.IDesignService;
import com.cubicpark.mechanic.domain.service.express.IExpressService;
import com.cubicpark.mechanic.domain.service.production.ICheckProductService;
import com.cubicpark.mechanic.domain.service.production.IProductMaterialsService;
import com.cubicpark.mechanic.domain.service.production.IProductionService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/production")
public class ProductionController {

    @Autowired
    private IProductionService productionService;

    @Autowired
    private IProductMaterialsService productMaterialsService;

    @Autowired
    private IExpressService expressService;

    @Autowired
    private ICheckProductService checkProductService;

    @Autowired
    private IDesignService designService;

    /**
     * 〈一句话功能简述〉<br>
     * 跳到列表页面
     *
     * @param
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=index")
    public String index(Model model,@RequestParam(value = "function",required = false) String function){
        List<ProductionDTO> productionDTOList = productionService.selectAll();
        List<ProductionDTO> unProductedList = new ArrayList<>();
        //查找出未开始生产的工单
        if(StringUtils.isNotBlank(function)){
            if(function.equals("dispose")){
                //生产工单处理
                for(ProductionDTO productionDTO : productionDTOList){
                    if(productionDTO.getStatus() == 0 || productionDTO.getStatus() == 7 || productionDTO.getStatus() == 6 || productionDTO.getStatus() == 5){ //0状态为生产工单待处理 7为生产工单原料被拒绝
                        unProductedList.add(productionDTO);
                    }
                }
                //如果原料都为已出库状态并且设计工单审核成功,则可下发生产工单
                boolean canProduct = false;
                for(ProductionDTO productionDTO : unProductedList){
                    List<ProductMaterialsDTO> productMaterialsDTOS = productMaterialsService.selectByProductCode(productionDTO.getProductCode());
                    Design design = designService.selectByContractCode(productionDTO.getContractCode());
                    if(productMaterialsDTOS.size() > 0 && design != null){
                        for(ProductMaterialsDTO productMaterialsDTO : productMaterialsDTOS){
                            if(productMaterialsDTO.getProcurementStatus() == 7){ //7为原料已出库
                                canProduct = true;
                            }else{
                                canProduct = false;
                            }
                        }
                    }else{
                        canProduct = false;
                    }

                    if(canProduct){
                        //可以生产
                        productionDTO.setStatus(6); //6为可生产状态
                        productionService.updateById(productionDTO);
                    }
                }


            }else if(function.equals("productionDispose")){
                //原料工单处理
                for(ProductionDTO productionDTO : productionDTOList){
                    if(productionDTO.getStatus() != 0){ //0状态为生产工单待处理
                        unProductedList.add(productionDTO);
                    }
                }
            }else if(function.equals("qualityTesting")){
                //质检处理
                for(ProductionDTO productionDTO : productionDTOList){
                    if(productionDTO.getStatus() == 2){ //2状态为生产完成
                        unProductedList.add(productionDTO);
                    }
                }
            }
            model.addAttribute("function",function);
            model.addAttribute("productionDTOList",unProductedList);
        }else {
            //生产工单管理查询出所有的
            model.addAttribute("productionDTOList",productionDTOList);
        }
        return "production/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转开始生产页面
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initStartProduct")
    public String initStartProduct(Model model,Integer id){
        model.addAttribute("id",id);
        return "production/startProduct";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 开始生产
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=startProduct")
    public void startProduct(HttpServletResponse response, HttpServletRequest request,ProductionDTO productionDTO){
        ProductionDTO production = productionService.selectById(productionDTO.getId());
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        productionDTO.setHandlerCode(employeeDTO.getName());
        productionDTO.setStatus(Constants.STARTPRODUCT);
        int low = productionService.updateById(productionDTO);
        if(low > 0){
            //开始生产成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else {
            //开始生产失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试!");
        }
    }


    /**
     * 〈一句话功能简述〉<br>
     * 产品的明细
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=detail")
    public String detail(String productCode,Model model){
        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);
        model.addAttribute("productionDTO",productionDTO);
        return "production/productionDetail";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 生产完成
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=productComplete")
    public void productComplete(HttpServletRequest request,HttpServletResponse response,Integer id){
        ProductionDTO productionDTO = productionService.selectById(id);
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        productionDTO.setStatus(Constants.FINISHPRODUCT);
        productionDTO.setHandlerCode(employeeDTO.getName());
        int low = productionService.updateById(productionDTO);
        if(low > 0){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else {
            //插入失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试!");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到质检处理页面
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initQualityTesting")
    public String initQualityTesting(String productCode,Model model){
        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);
        model.addAttribute("productCode",productCode);
        model.addAttribute("productionDTO",productionDTO);
        return "production/startCheck";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 质检处理
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=qualityCheck")
    public void qualityCheck(ProductionDTO productionDTO,HttpServletRequest request,HttpServletResponse response){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        ProductionDTO production = productionService.selectByProductCode(productionDTO.getProductCode());
        production.setHandlerCode(employeeDTO.getName());
        production.setStatus(productionDTO.getStatus());
        production.setQualityRemark(productionDTO.getRemark());
        //更新
        int row = productionService.updateById(production);
        if(row > 0){
            //提交成功,生成物流工单
            expressService.create(production.getContractCode());
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else {
            //提交失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试!");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 检验项页面
     *
     * @param model, productCode
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=addCheckItem")
    public String addCheckItem(Model model,String productCode){
        List<CheckProductDTO> checkProductDTOList = checkProductService.selectByproductCode(productCode);
        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);

        model.addAttribute("productionDTO",productionDTO);
        model.addAttribute("productCode",productCode);
        model.addAttribute("checkProductDTOList",checkProductDTOList);
        return "production/checkProduct/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 添加检验项9
     *
     * @param model, productCode
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(Model model,String productCode){
        model.addAttribute("productCode",productCode);
        return "production/checkProduct/modify";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 添加检测项
     *
     * @param response, checkProductDTO
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public void add(HttpServletResponse response, CheckProductDTO checkProductDTO){
        boolean insert = checkProductService.insert(checkProductDTO);
        if(insert){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"失败,请重试");
        }
    }

    @RequestMapping(params = "method=delete")
    public void delete(Integer id,HttpServletResponse response){
        boolean delete = checkProductService.deleteById(id);
        if(delete){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 重新填写原料单
     *
     * @param model, id
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=rehandle")
    public String rehandle(Model model,Integer id){
        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
        productMaterialsDTO.setProcurementStatus(0);//0为默认状态
        productMaterialsService.updateById(productMaterialsDTO);
        ProductionDTO productionDTO = productionService.selectByProductCode(productMaterialsDTO.getProductCode());
        productionService.updateById(productionDTO);
        model.addAttribute("productCode",productionDTO.getProductCode());
        return "production/productMaterialsManage";
    }

}
