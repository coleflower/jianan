package com.cubicpark.mechanic.domain.service.base;

import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
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
public interface IPositionService extends IAssortmentBseService<PositionDTO> {
	
	/**
	 * 
	 * 功能描述: <br>
	 * 根据职位编码获取职位信息
	 *
	 * @param positionCode
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
    PositionDTO getPositionByCode(String positionCode);
}
