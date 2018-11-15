package com.cubicpark.mechanic.controller.car;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.domain.dto.car.CarApplicationDTO;
import com.cubicpark.mechanic.domain.dto.car.CarDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.car.ICarApplicationService;
import com.cubicpark.mechanic.domain.service.car.ICarService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("carApplication")
public class CarApplicationController {

    @Autowired
    private ICarService carService;

    @Autowired
    private ICarApplicationService carApplicationService;

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到车辆使用页面
     *
     * @param model, carApplicationDTO
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=borrowCar")
    public String borrowCar(Model model, CarApplicationDTO carApplicationDTO){
        if(carApplicationDTO == null){
            return null;
        }
        List<CarDTO> carDTOList = carService.selectAll();
        model.addAttribute("carDTOList",carDTOList);

        return "car/useCar";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 添加车辆审核
     *
     * @param request, carApplicationDTO, response
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public void add(HttpServletRequest request,CarApplicationDTO carApplicationDTO, HttpServletResponse response){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        //生成code
        String carUseCode = MenthaCodeUtil.generateMenthaCode("carApplication", 18);
        carApplicationDTO.setCarUseCode(carUseCode);
        carApplicationDTO.setEmployeeCode(employeeDTO.getEmployeeCode());
        carApplicationDTO.setEmployeeName(employeeDTO.getName());
        carApplicationDTO.setCreateTime(new Timestamp(System.currentTimeMillis()));

        boolean insert = carApplicationService.insert(carApplicationDTO);
        if(insert){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"失败,请重试");
        }

    }

    /**
     * 〈一句话功能简述〉<br>
     * 用户使用车辆列表
     *
     * @param model, request
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=useList")
    public String useList(Model model,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");

        List<CarApplicationDTO> carApplicationDTOList = carApplicationService.selectByEmployeeCode(employeeDTO.getEmployeeCode());
        for(CarApplicationDTO carApplicationDTO : carApplicationDTOList){
            CarDTO carDTO = carService.selectById(carApplicationDTO.getCarId());
            carApplicationDTO.setCarId(carDTO.getBrank() + " " + carDTO.getType());
            carApplicationDTO.setRoadHaul(carDTO.getTotalRoadHaul());
        }

        model.addAttribute("carApplicationDTOList",carApplicationDTOList);
        return "car/useList";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 结束使用
     *
     * @param model, id
     * @return java.lang.String
     *  @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initFinishUse")
    public String initFinishUse(Model model,Integer id,Double roadHaul){

        CarApplicationDTO carApplicationDTO = carApplicationService.selectById(id);

        model.addAttribute("roadHaul",roadHaul);
        model.addAttribute("id",id);
        return "car/finishUse";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 结束使用车辆
     *
     * @param response, carApplicationDTO, oldRoadHaul
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=finishUse")
    public void finishUse(HttpServletResponse response,CarApplicationDTO carApplicationDTO,Double oldRoadHaul){
        CarApplicationDTO carApplicationDTO1 = carApplicationService.selectById(carApplicationDTO.getId());
        BigDecimal newRoadHaul = new BigDecimal(Double.toString(carApplicationDTO.getRoadHaul()));
        BigDecimal oldRoadHaul1 = new BigDecimal(Double.toString(oldRoadHaul));
        BigDecimal roadHaul = newRoadHaul.subtract(oldRoadHaul1);
        Double roadHaul1 = roadHaul.doubleValue(); //车辆所走的公里数

        carApplicationDTO1.setRoadHaul(roadHaul1);
        carApplicationDTO1.setRefuelingNumber(carApplicationDTO.getRefuelingNumber());
        carApplicationDTO1.setEndTime(new Timestamp(System.currentTimeMillis()));
        boolean result = carApplicationService.updateById(carApplicationDTO1);

        if(result){
            CarDTO carDTO = carService.selectById(carApplicationDTO1.getCarId());
            carDTO.setStatus(0);
            carDTO.setTotalRefuelingNumber(carApplicationDTO.getRefuelingNumber() + carDTO.getTotalRefuelingNumber());
            carDTO.setTotalRoadHaul(carApplicationDTO.getRoadHaul());
            carService.updateById(carDTO);

            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else {
            AjaxUtil.ajaxJsonSucMessage(response,"操作失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳到车辆使用记录
     *
     * @param carId, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=carUseList")
    public String carUseList(Integer carId,Model model){
        List<CarApplicationDTO> carApplicationDTOS = carApplicationService.selectByCarId(carId.toString());
        model.addAttribute("carApplicationDTOList",carApplicationDTOS);
        model.addAttribute("carId",carId);
        return "car/carUseList";
    }

    @RequestMapping(params = "method=selectByEmployeeNameAndDate")
    public String selectByEmployeeNameAndDate(Model model,@RequestParam(value = "carId",required = false)String carId,
                                              @RequestParam(value = "employeeName",required = false) String employeeName,
                                              @RequestParam(value = "startDate",required = false)String startDate,
                                              @RequestParam(value = "endDate",required = false)String endDate){

        //查询列表数据
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Timestamp tt = new Timestamp(System.currentTimeMillis());
        if(StringUtils.isNotBlank(startDate)){
            ts = Timestamp.valueOf(startDate);
        }else {
            ts = null;
        }
        if(StringUtils.isNotBlank(endDate)){
            tt = Timestamp.valueOf(endDate);
        }else{
            tt = null;
        }

        List<CarApplicationDTO> carApplicationDTOS = carApplicationService.selectByEmployeeNameAndDate(carId,employeeName, ts, tt);
        model.addAttribute("carApplicationDTOList",carApplicationDTOS);
        model.addAttribute("employeeName",employeeName);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("carId",carId);
        return "car/carUseList";
    }



}
