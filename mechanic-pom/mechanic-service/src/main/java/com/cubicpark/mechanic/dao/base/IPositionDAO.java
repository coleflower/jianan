package com.cubicpark.mechanic.dao.base;

import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.firstji.mentha.dao.assortment.IAssortmentBaseDAO;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 职位DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IPositionDAO extends IAssortmentBaseDAO<PositionDTO> {
    
    /**
     * 
     * 功能描述: <br>
     * 根据编码获取职位信息
     *
     * @param positionCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    PositionDTO getPositionByCode(String positionCode);
}
