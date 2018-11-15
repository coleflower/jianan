package com.cubicpark.mechanic.controller.production;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductionDTO;
import com.cubicpark.mechanic.domain.dto.storage.Stock;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementService;
import com.cubicpark.mechanic.domain.service.production.IProductMaterialsService;
import com.cubicpark.mechanic.domain.service.production.IProductionService;
import com.cubicpark.mechanic.domain.service.storage.IStockService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.production.ProductionMaterialVO;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/productMaterials")
public class ProductMaterialsController {

    @Autowired
    private IProductMaterialsService productMaterialsService;

    @Autowired
    private IProductionService productionService;

    @Autowired
    private IStockService stockService;

    @Autowired
    private IProcurementService procurementService;

    @RequestMapping(params = "method=index")
    public String index(Model model){
        List<ProductMaterialsDTO> productMaterialsDTOList = productMaterialsService.selectAllMaterials();
        model.addAttribute("productMaterialsDTOList",productMaterialsDTOList);
        model.addAttribute("selectAll",true);
        return "production/productMaterialsManage";
    }

    @RequestMapping(params = "method=initAdd")
    public String initAdd(Model model,@RequestParam("productCode") String productCode,@RequestParam(value = "secondaryApplication",required = false) String secondaryApplication){

        if(StringUtils.isNotBlank(secondaryApplication)){
            //原料二次审核
            model.addAttribute("secondaryApplication",secondaryApplication);
        }

        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);
        model.addAttribute("productionDTO",productionDTO);
        model.addAttribute("productCode",productCode);
        return "production/modify";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 列表页
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=productMaterialsList")
    public String productMaterialsList(Model model,@RequestParam("productCode") String productCode,@RequestParam(value = "function",required = false) String function){
        List<ProductMaterialsDTO> productMaterialsDTOList = productMaterialsService.selectByProductCode(productCode);
        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);
        if(StringUtils.isNotBlank(function)){
            //说明是审核页面
            model.addAttribute("function",function);
        }else{
            if(productionDTO == null){
                //未发送审核
                model.addAttribute("ProductCodeIsExist",true);
                model.addAttribute("productMaterialsStatus",0);
            }else {
                //已发送审核
                model.addAttribute("ProductCodeIsExist",false);
            }
        }

        model.addAttribute("productCode",productCode);
        model.addAttribute("productionDTO",productionDTO);
        model.addAttribute("productMaterialsDTOList",productMaterialsDTOList);
        return "production/productMaterialsManage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 添加原料
     *
     * @param response, request, productMaterialsDTO
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public void add(HttpServletResponse response, HttpServletRequest request,ProductMaterialsDTO productMaterialsDTO){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        productMaterialsDTO.setHandlerCode(employeeDTO.getEmployeeCode());

        //生成code
        String productMaterialsCode = MenthaCodeUtil.generateMenthaCode("productmaterials", 18);
        productMaterialsDTO.setProductMaterialsCode(productMaterialsCode);

        //查询库存
        List<Stock> stockList = queryStock(productMaterialsDTO);
        Stock stock = new Stock();
        Integer number = 0;
        for(Stock stock1 : stockList){
            number += Integer.valueOf(stock1.getNumber());
        }
        stock.setNumber(number.toString());

        if(stockList.size() != 0){
            if(Integer.valueOf(stock.getNumber()) >= productMaterialsDTO.getProductMaterialsCount()){
                //如果有库存直接减去,并修改状态,库存大于需要数量
                StringBuffer stockLog = new StringBuffer();
                for(Stock stock1 : stockList){
                    if(Integer.valueOf(stock1.getNumber()) >= productMaterialsDTO.getProductMaterialsCount()){
                        //减库存
                        stockLog.append(";"+stock1.getStockCode()+";"+productMaterialsDTO.getProductMaterialsCount());
                        Integer result = Integer.valueOf(stock1.getNumber())-productMaterialsDTO.getProductMaterialsCount();
                        stock1.setNumber(result.toString());
                        stockService.updateById(stock1);
                        break;
                    }else{
                        //添加减库存日志
                        stockLog.append(";"+stock1.getStockCode()+";"+stock1.getNumber());
                        productMaterialsDTO.setProductMaterialsCount(productMaterialsDTO.getProductMaterialsCount()-Integer.valueOf(stock1.getNumber()));
                        stock1.setNumber("0");
                        stockService.updateById(stock1);
                    }
                }
                productMaterialsDTO.setStockLog(stockLog.toString());

                Integer remainInventory = Integer.valueOf(stock.getNumber())-productMaterialsDTO.getProductMaterialsCount(); //剩余库存
                stock.setNumber(remainInventory.toString());
                stockService.updateById(stock);
                productMaterialsDTO.setProcurementStatus(3); //3为有货状态
            }else{
                //库存小于需要数量,说明缺货
                productMaterialsDTO.setProcurementStatus(1); //1为缺货状态
            }

        }else {
            //库存不足,提示不足
            productMaterialsDTO.setProcurementStatus(1); //1为缺货状态
        }

        int insert = 0;
        if(org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getId())){
            //如果id为null ,说明是新增
            insert = productMaterialsService.insert(productMaterialsDTO);
        }else{
            //修改
            insert = productMaterialsService.updateById(productMaterialsDTO);

            //更改采购单
            ProcurementDTO procurementDTO = procurementService.selectByProductMaterialsId(productMaterialsDTO.getId());
            procurementDTO.setName(productMaterialsDTO.getProductMaterialsName());
            procurementDTO.setMaterial(productMaterialsDTO.getProductMaterials());
            procurementDTO.setCount(productMaterialsDTO.getProductMaterialsCount());
            procurementDTO.setCodeName(productMaterialsDTO.getProductMaterialsStandardCode());
            procurementDTO.setStatus(0); //0为待处理状态
            procurementDTO.setSupplier(null);
            procurementDTO.setQuotedPrice(null);
            procurementDTO.setOperator(null);
            procurementService.updateById(procurementDTO);
        }

        if(insert > 0){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //插入失败
            AjaxUtil.ajaxJsonErrMessage(response,"添加失败,请重试!");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 提交原料单
     *
     * @param response, productCode
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=submitProductMaterials")
    public void submitProductMaterials(HttpServletResponse response,@RequestParam("productCode") String productCode,@RequestParam(value = "productMaterialsCode",required = false) String productMaterialsCode){
        List<ProductMaterialsDTO> productMaterialsDTOList = new ArrayList<>();
        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);

        if(StringUtils.isNotBlank(productMaterialsCode)){
            //原料二次申请提交
            ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectByProductMaterialsCode(productMaterialsCode);
            productMaterialsDTOList.add(productMaterialsDTO);
        }else{
            productMaterialsDTOList = productMaterialsService.selectByProductCode(productCode);
        }


        for(ProductMaterialsDTO productMaterialsDTO : productMaterialsDTOList){

            if(productMaterialsDTO.getProcurementStatus() == 3){ //3为有货状态
                //如果状态为有货
                productMaterialsDTO.setProcurementStatus(5); //5为等待出库中
            }else if(productMaterialsDTO.getProcurementStatus() == 1){ //1为缺货状态 7为采购拒绝状态
                //查询库存
                List<Stock> stockList = queryStock(productMaterialsDTO);
                Stock stock = new Stock();
                Integer number = 0;
                for(Stock stock1 : stockList){
                    number += Integer.valueOf(stock1.getNumber());
                }
                stock.setNumber(number.toString());
                //缺货,或者货源不足
                if(stockList.size() != 0){
                    //仓库有库存,减去已有的库存,在申请采购
                    if(Integer.valueOf(stock.getNumber())-productMaterialsDTO.getProductMaterialsCount() < 0){
                        List<Integer> stauts = new ArrayList<>();
                        stauts.add(2);
                        boolean isSame = false;
                        //查询状态为2的所有原料工单
                        List<ProductMaterialsDTO> productMaterialsDTOS = productMaterialsService.selectByStatus(stauts);
                        //查看有没有该产品的原料单在采购中
                        for(ProductMaterialsDTO productMaterialsDTO1 : productMaterialsDTOS){
                            if(productMaterialsDTO1.getProductMaterialsName().equals(productMaterialsDTO.getProductMaterialsName())
                                    && productMaterialsDTO1.getProductMaterials().equals(productMaterialsDTO.getProductMaterials())
                                    && productMaterialsDTO1.getProductMaterialsStandardCode().equals(productMaterialsDTO.getProductMaterialsStandardCode())){
                                //说明有待处理的原料,里面的剩余库存已被占用
                                isSame = true;
                            }
                        }
                        if(isSame){
                            //剩余库存已被占用
                            //创建采购清单
                            procurementService.insert(productMaterialsDTO.getProductMaterialsCount(),productMaterialsDTO);
                        }else{
                            //剩余库存没有被占用
                            //创建采购清单
                            procurementService.insert(productMaterialsDTO.getProductMaterialsCount()-Integer.valueOf(stock.getNumber()),productMaterialsDTO);
                        }

                    }
                }else{
                    //仓库没有库存,直接申请采购
                    procurementService.insert(productMaterialsDTO.getProductMaterialsCount(),productMaterialsDTO);
                }
                productMaterialsDTO.setProcurementStatus(2); // 2为缺货,采购中
            }
            productMaterialsService.updateById(productMaterialsDTO);
        }
        productionDTO.setStatus(5); //5为原料待出库状态
        int row = productionService.updateById(productionDTO);
        if(row > 0){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //插入失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 编辑原料单
     *
     * @param id, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=modify")
    public String modify(@RequestParam("id") Integer id,Model model,@RequestParam(value = "applicationReason",required = false) String applicationReason){

        if(StringUtils.isNotBlank(applicationReason)){
            //原料二次提交
            model.addAttribute("secondaryApplication",applicationReason);
        }

        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
        model.addAttribute("productMaterialsDTO",productMaterialsDTO);
        model.addAttribute("productCode",productMaterialsDTO.getProductCode());
        return "production/modify";
    }

    @RequestMapping(params = "method=selectProductCode")
    public void selectProductCode(Model model,HttpServletResponse response,String productCode){
        ProductionDTO productionDTO = productionService.selectByProductCode(productCode);
        if(productionDTO == null){
            //没有该产品工单
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else {
            AjaxUtil.ajaxJsonSucMessage(response,"");
        }
    }


    /**
     * 〈一句话功能简述〉<br>
     * 删除原料单,并恢复库存
     *
     * @param id, response
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=del")
    public void del(Integer id,HttpServletResponse response){
        if(id == null){
            AjaxUtil.ajaxJsonErrMessage(response,"参数错误");
        }

        //恢复库存
        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
        String[] stockLog = productMaterialsDTO.getStockLog().split(";");
        for(int i = 1;i < stockLog.length;i=i+2){
            Stock stock = stockService.selectByStockCode(stockLog[i]);
            Integer count = Integer.valueOf(stockLog[i+1]) + Integer.valueOf(stock.getNumber());
            stock.setNumber(count.toString());
            stockService.updateById(stock);
        }

        productMaterialsService.deleteById(id);

        AjaxUtil.ajaxJsonSucMessage(response,"200");
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到原料与库存对比页
     *
     * @param productCode, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initOutStorage")
    public String initOutStorage(String productCode,Model model){
        List<ProductMaterialsDTO> productMaterialsDTOList = productMaterialsService.selectByProductCode(productCode);
    //    List<ProductionMaterialVO> productionMaterialVOList = selectProductionMaterialVO(productMaterialsDTOList);
     //   model.addAttribute("productionMaterialVOList",productionMaterialVOList);
        return "production/outStorage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 查询库存
     *
     * @param productMaterialsDTO
     * @return com.cubicpark.mechanic.domain.dto.storage.Stock
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<Stock> queryStock(ProductMaterialsDTO productMaterialsDTO){
        List<Stock> stockList = new ArrayList<>();
        //查出该原料的库存
        if(org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())
                && org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterials())){
            //如果标准代号是空的,说明是非标准件,并且材料为空
            stockList = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),null,null);
        }else if(org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())
                && !org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterials())){
            //标准代号为空时,材料不为空
            stockList = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),productMaterialsDTO.getProductMaterials(),null);
        }else if(!org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())){
            //标准代号不为空
            stockList = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),productMaterialsDTO.getProductMaterials(),productMaterialsDTO.getProductMaterialsStandardCode());
        }else if(!org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())
                && org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterials())){
            //当材料为空时,根据名字和代号查询
            stockList = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),null,productMaterialsDTO.getProductMaterialsStandardCode());
        }
        return stockList;
    }

    /**
     * 将产品原料封装成ProductionMaterialVO
     * @param productMaterialsDTOList
     * @return
     */
//    private List<ProductionMaterialVO> selectProductionMaterialVO(List<ProductMaterialsDTO> productMaterialsDTOList){
//        List<ProductionMaterialVO> productionMaterialVOList = new ArrayList<>();
//        ProductionMaterialVO productionMaterialVO = new ProductionMaterialVO();
//
//        for(ProductMaterialsDTO productMaterialsDTO : productMaterialsDTOList){
//            //标准代号和材料都为空时
//            if(org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())
//                    && org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterials())){
//
//                //如果标准代号是空的,说明是非标准件
//                Stock stock = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),null,null);
//                if(stock == null){
//                    //仓库没有该元件
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"非标件",0);
//                }else{
//                    //有库存
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"非标件",Integer.valueOf(stock.getNumber()));
//                }
//            }else if(org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())
//                    && !org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterials())){
//                //标准代号为空时,材料不为空
//                Stock stock = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),productMaterialsDTO.getProductMaterials(),null);
//                if(stock == null){
//                    //仓库没有该元件
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"非标件",0);
//                }else{
//                    //有库存
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"非标件",Integer.valueOf(stock.getNumber()));
//                }
//            }else if(!org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())){
//                //标准代号不为空
//                Stock stock = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),productMaterialsDTO.getProductMaterials(),productMaterialsDTO.getProductMaterialsStandardCode());
//                if(stock == null){
//                    //仓库没有该元件
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"标准件",0);
//                }else{
//                    //有库存
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"标准件",Integer.valueOf(stock.getNumber()));
//                }
//            }else if(!org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterialsStandardCode())
//                    && org.springframework.util.StringUtils.isEmpty(productMaterialsDTO.getProductMaterials())){
//                //当材料为空时,根据名字和代号查询
//                Stock stock = stockService.selectByNameAndNameCodeAndMaterial(productMaterialsDTO.getProductMaterialsName(),null,productMaterialsDTO.getProductMaterialsStandardCode());
//                if(stock == null){
//                    //仓库没有该元件
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"标准件",0);
//                }else{
//                    //有库存
//                    productionMaterialVO = packageProductionMaterialVO(productMaterialsDTO,"标准件",Integer.valueOf(stock.getNumber()));
//                }
//            }
//            productionMaterialVOList.add(productionMaterialVO);
//        }
//
//        return productionMaterialVOList;
//    }

    /**
     * 〈一句话功能简述〉<br>
     * 封装ProductionMaterialVO
     *
     * @author qwc-01
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private ProductionMaterialVO packageProductionMaterialVO(ProductMaterialsDTO productMaterialsDTO,String type,Integer count){
        ProductionMaterialVO productionMaterialVO = new ProductionMaterialVO();

        //设置状态
        if(productMaterialsDTO.getProcurementStatus() == 2 || productMaterialsDTO.getProcurementStatus() == 4){//2为采购中 4为采购拒绝
            //说明处于采购状态
            if(productMaterialsDTO.getProductMaterialsCount() <= count){
                productionMaterialVO.setStatus(3); //3为库存有货状态
            }else{
                productionMaterialVO.setStatus(productMaterialsDTO.getProcurementStatus());
            }
        }else if(productMaterialsDTO.getProcurementStatus() == 0){
            //还没有进行采购
            if(productMaterialsDTO.getProductMaterialsCount() > count){
                productionMaterialVO.setStatus(1); //1为库存缺货状态
            }else{
                productionMaterialVO.setStatus(3); //3为库存有货状态
            }
        }else if(productMaterialsDTO.getProcurementStatus() == 5 || productMaterialsDTO.getProcurementStatus() == 6){ //5为申请出库中 6为申请部分出库中
            //处于有料状态或缺料状态要申请出库
            productionMaterialVO.setStatus(productMaterialsDTO.getProcurementStatus());
        }else if(productMaterialsDTO.getProcurementStatus() == 7 || productMaterialsDTO.getProcurementStatus() == 8 || productMaterialsDTO.getProcurementStatus() == 9){  //7为出库成功 8为部分出库成功  9出库拒绝
            productionMaterialVO.setStatus(productMaterialsDTO.getProcurementStatus());
        }

        productionMaterialVO.setName(productMaterialsDTO.getProductMaterialsName());
        productionMaterialVO.setType(type);
        productionMaterialVO.setMaterial(productMaterialsDTO.getProductMaterials());
        productionMaterialVO.setStorageCount(count);
        productionMaterialVO.setCount(productMaterialsDTO.getProductMaterialsCount());
        productionMaterialVO.setCodeName(productMaterialsDTO.getProductMaterialsStandardCode());
        productionMaterialVO.setProductionMaterialId(productMaterialsDTO.getId());

        return productionMaterialVO;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 申请出库
     *
     * @param response, id
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=applyOutStorage")
    public void initOutStorage(HttpServletResponse response,Integer id,Integer storageCount){ //storageCount为仓库数量
        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
        productMaterialsDTO.setProcurementStatus(5); //5为申请出库状态

        int row = productMaterialsService.updateById(productMaterialsDTO);
        if(row > 0){
            //提交成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //提交失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     *库管原料出库审核
     *
     * @param model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=outStorage")
    public String outStorage(Model model){
        List<Integer> outStorageStatus = new ArrayList<>();
        outStorageStatus.add(5);// 申请出库状态
        List<ProductMaterialsDTO> productMaterialsDTOList = productMaterialsService.selectByStatus(outStorageStatus);//5为申请出库状态
    //    List<ProductionMaterialVO> productionMaterialVOList = selectProductionMaterialVO(productMaterialsDTOList);
        model.addAttribute("productMaterialsDTOList",productMaterialsDTOList);
        return "production/outStorageProductMaterialsManage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 仓管同意出库
     *
     * @param id, status
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=agreeOutStorage")
    public void agreeOutStorage(HttpServletResponse response,Integer id){
        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
        productMaterialsDTO.setProcurementStatus(7); //7为已出库状态
        //状态改为出库状态
        int i = productMaterialsService.updateById(productMaterialsDTO);
        if(i > 0){
            //更改成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //更改失败
            AjaxUtil.ajaxJsonSucMessage(response,"失败,请重试");
        }

    }

    /**
     * 〈一句话功能简述〉<br>
     * 拒绝出库
     *
     * @param id
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initRefuseOutStorage")
    public String initRefuseOutStorage(Integer id,Model model){
        model.addAttribute("id",id);
        model.addAttribute("initRefuseOutStorage","initRefuseOutStorage");
        return "production/startCheck";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 拒绝出库
     *
     * @param response, id, remark
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=refuseOutStorage")
    public void refuseOutStorage(HttpServletResponse response,Integer id,String remark){
        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
        productMaterialsDTO.setProcurementStatus(9); //9为出库拒绝状态

        int row = productMaterialsService.updateById(productMaterialsDTO);
        if(row >0){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //插入失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 出库拒绝备注
     *
     * @param model, id
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=outStorageRefuseDetail")
    public String outStorageRefuseDetail(Model model,Integer id){
        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(id);
      //  model.addAttribute("remark",productMaterialsDTO.getOutStorageRemark());
        model.addAttribute("reason","outStorageRefuseDetail");
        return "procurement/startCheck";
    }

}
