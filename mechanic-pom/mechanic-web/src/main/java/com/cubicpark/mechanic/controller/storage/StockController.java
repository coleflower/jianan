package com.cubicpark.mechanic.controller.storage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.storage.Stock;
import com.cubicpark.mechanic.domain.dto.storage.Storage;
import com.cubicpark.mechanic.domain.service.storage.IStockService;
import com.cubicpark.mechanic.domain.service.storage.IStorageService;
import com.cubicpark.mechanic.util.AjaxObject;
import com.cubicpark.mechanic.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 *  入库/出库控制器
 * @author Surging
 * @create 2018/9/10
 * @since 1.0.0
 */
@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IStorageService storageService;

    /**
     * 出库/入库
     * @param request
     * @param stock
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Object save(HttpServletRequest request, Stock stock){
        EmployeeDTO employee = getMember(request);
        stock.setEmployeeCode(employee.getEmployeeCode());
        stock.setStockCode(KeyUtil.genUniqueKey());
        if (stock.getType() == 0){
            return stockService.updateAddStock(stock) ? AjaxObject.ok("入库成功") : AjaxObject.error("添加失败");
        }
        return stockService.updateDelStock(stock) ? AjaxObject.ok("出库成功") : AjaxObject.error("添加失败");
    }

    /**
     * 出库/入库
     * @param type
     * @param model
     * @return
     */
    @GetMapping("/preSave")
    public String preSave(@RequestParam(value = "type") Integer type, Model model) {
        List<Storage> storageList = storageService.selectList(null);
        model.addAttribute("storageList", storageList);
        model.addAttribute("type", type);
        return "storage/stock/add";
    }

    /**
     * 我的入库出库记录
     * @param model
     * @param request
     * @return
     */
    public String list(Model model, HttpServletRequest request){
        EmployeeDTO employee = getMember(request);
        Stock stock = new Stock();
        stock.setEmployeeCode(employee.getEmployeeCode());
        EntityWrapper<Stock> entityWrapper = new EntityWrapper<>(stock);
        List<Stock> stockList = stockService.selectList(entityWrapper);
        model.addAttribute("stockList", stockList);
        return "storage/stock/list";
    }
}
