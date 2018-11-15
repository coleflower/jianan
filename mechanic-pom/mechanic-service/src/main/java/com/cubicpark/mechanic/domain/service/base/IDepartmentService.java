package com.cubicpark.mechanic.domain.service.base;

import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.firstji.mentha.domain.service.assortment.IAssortmentBseService;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 菜单信息
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IDepartmentService extends IAssortmentBseService<DepartmentDTO> {
	
	/**
	 * 
	 * 功能描述: <br>
	 * 根据部门编码获取部门信息
	 *
	 * @param departmentCode
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	DepartmentDTO getDepartmentByCode(String departmentCode);
}
