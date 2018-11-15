package com.cubicpark.mechanic.controller.storage;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.domain.dto.storage.Storage;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.storage.IStorageService;
import com.cubicpark.mechanic.exception.ServiceException;
import com.cubicpark.mechanic.util.AjaxObject;
import com.cubicpark.mechanic.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 *  仓库控制器
 *
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
@Controller
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private IStorageService storageService;

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 仓库列表
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "storageName", required = false) String storageName) {
        /*Map<String, Object> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(storageName)){
            queryMap.put("storage_name", storageName);
        }*/
        Storage storage = new Storage();
        if (!StringUtils.isEmpty(storageName)){
            storage.setStorageName(storageName);
        }
        EntityWrapper<Storage> entityWrapper = new EntityWrapper<>(storage);
        List<Storage> storageList = storageService.selectList(entityWrapper);
        // TODO 获取员工信息 根据CODE 查出员工名称
        //storageList.stream().forEach(e -> e.setManager(employeeService.getEmployeeByCode(e.getManager()).getName()));
        model.addAttribute("storageList",storageList);
        model.addAttribute("param", storage);
        return "storage/list";
    }

    @GetMapping("/preSave")
    public String preSave(Model model, @RequestParam(value = "id", required = false) Integer id) {
        // TODO 所有员工信息 部门

        if (!StringUtils.isEmpty(id)) {
            Storage storage = storageService.selectById(id);
            model.addAttribute("storage", storage);
        }
        return "storage/add";
    }

    /**
     * 新增/修改
     * @param storage
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Object save(Storage storage) {
        if (storage.getId() == null) {
            storage.setStorageCode(KeyUtil.genUniqueKey());
            return storageService.insert(storage) ? AjaxObject.ok("添加成功") : AjaxObject.error("添加失败");
        } else {
            return storageService.updateById(storage) ? AjaxObject.ok("修改成功") : AjaxObject.error("修改失败");
        }
    }

    /**
     * 删除仓库
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(@RequestParam(value = "id", required = false) Integer id) {
        Storage storage = storageService.selectById(id);
        if (storage == null) {
            throw new ServiceException(CommonErrorCode.STORAGE_NOT_EXIST);
        }
        // 仓库里面是否有货架/堆货区
        if (storageService.isEmpty(storage.getStorageCode())){
            return storageService.deleteById(id) ? AjaxObject.ok("删除成功") : AjaxObject.error("删除失败");
        }
        return AjaxObject.error(CommonErrorCode.STORAGE_NOT_EMPTY.getDesc());
    }
}
