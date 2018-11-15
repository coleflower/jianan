package com.cubicpark.mechanic.controller.storage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.domain.dto.storage.Rack;
import com.cubicpark.mechanic.domain.service.storage.IRackService;
import com.cubicpark.mechanic.exception.ServiceException;
import com.cubicpark.mechanic.util.AjaxObject;
import com.cubicpark.mechanic.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 *  货架/堆货区控制器
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
@Controller
@RequestMapping("/rack")
public class RackController {

    @Autowired
    private IRackService rackService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "storageCode") String storageCode, @RequestParam(value = "type", required = false) Integer type, Model model) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("storage_code",storageCode);
        if (!StringUtils.isEmpty(type)){
            queryMap.put("type",type);
        }
        List<Rack> rackList = rackService.selectByMap(queryMap);
        model.addAttribute("rackList",rackList);
        model.addAttribute("param",queryMap);
        return "storage/rack/list";
    }

    @GetMapping("/preSave")
    public String preSave(Model model, @RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "storageCode") String storageCode) {
        if (!StringUtils.isEmpty(id)) {
            Rack rack = rackService.selectById(id);
            model.addAttribute("rack", rack);
        }
        model.addAttribute("storageCode", storageCode);
        return "storage/rack/add";
    }

    /**
     * 新增/修改
     * @param rack
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Object save(Rack rack) {
        if (rack.getId() == null) {
            rack.setRackCode(KeyUtil.genUniqueKey());
            return rackService.insert(rack) ? AjaxObject.ok("添加成功") : AjaxObject.error("添加失败");
        } else {
            return rackService.updateById(rack) ? AjaxObject.ok("修改成功") : AjaxObject.error("修改失败");
        }
    }

    /**
     * 删除货架/堆货区
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(@RequestParam(value = "id", required = false) Integer id) {
        Rack rack = rackService.selectById(id);
        if (rack == null) {
            throw new ServiceException(CommonErrorCode.RACK_NOT_EXIST);
        }
        if ("0".equals(rack.getStock())) {
            return rackService.deleteById(id) ? AjaxObject.ok("删除成功") : AjaxObject.error("删除失败");
        }
        return AjaxObject.error(CommonErrorCode.RACK_NOT_EMPTY.getDesc());
    }

    /**
     * 根据仓库编号查询货架信息
     * @param storageCode
     * @return JSON 货架信息
     */
    @ResponseBody
    @PostMapping("/getRack")
    public Object getRack(@RequestParam(value = "storageCode") String storageCode){
        Rack rack = new Rack();
        rack.setStorageCode(storageCode);
        List<Rack> rackList = rackService.selectList(new EntityWrapper<>(rack));
        return AjaxObject.ok(new AjaxObject().data(rackList));
    }
}
