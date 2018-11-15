package com.cubicpark.mechanic.dao.base;

import org.springframework.stereotype.Repository;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 序列DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Repository("seqDAO")
public interface ISeqDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 获取序列头
     *
     * @param seqName
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String getSeqHead(String seqName);
    
    /**
     * 
     * 功能描述: <br>
     * 获取序列值
     *
     * @param seqName
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String getNextSeqValue(String seqName);
}
