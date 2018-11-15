package com.cubicpark.mechanic.controller.car;

import com.cubicpark.mechanic.domain.dto.car.CarDTO;
import com.cubicpark.mechanic.domain.service.car.ICarService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {

    @Autowired
    private ICarService carService;

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到车辆列表页面
     *
     * @param model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=index")
    public String index(Model model){
        List<CarDTO> carDTOList = carService.selectAll();
        model.addAttribute(carDTOList);

        return "car/list";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 条状到添加页面
     *
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(){
        return "car/add";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 新增车辆
     *
     * @param response, carDTO
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public void add(HttpServletResponse response,CarDTO carDTO){

        boolean insert = false;
        if(org.springframework.util.StringUtils.isEmpty(carDTO.getId())){
            //id为空说明是添加
            carDTO.setStatus(0); //0为车辆未使用状态
            carDTO.setCreateTime(new Timestamp(System.currentTimeMillis()));

            insert = carService.insert(carDTO);
        }

        if(insert){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"新增失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 删除车辆
     *
     * @param id, response
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=del")
    public void del(Integer id,HttpServletResponse response){
        if(org.springframework.util.StringUtils.isEmpty(id)){
            //ID为空,参数错误
            AjaxUtil.ajaxJsonSucMessage(response,"参数错误");
            return;
        }

        boolean result = carService.deleteById(id);
        if(result){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"删除失败,请重试");
        }
    }


}
