package com.cubicpark.mechanic.dao.base;

import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.firstji.mentha.dao.assortment.IAssortmentBaseDAO;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 部门DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IDepartmentDAO extends IAssortmentBaseDAO<DepartmentDTO> {
    
    /**
     * 
     * 功能描述: <br>
     * 根据编码获取部门信息
     *
     * @param departmentCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    DepartmentDTO getDepartmentByCode(String departmentCode);
}
