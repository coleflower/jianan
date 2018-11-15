package com.cubicpark.mechanic.domain.service.storage;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.storage.Stock;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
public interface IStockService extends IService<Stock> {

    /**
     * 入库
     * @param stock
     * @return
     */
    boolean updateAddStock(Stock stock);

    /**
     * 出库
     * @param stock
     * @return
     */
    boolean updateDelStock(Stock stock);

    /**
     * 查询所有
     * @return
     */
    List<Stock> selectAll();


    /**
     * 查询标准件(根据名称和标准代号查询)
     * @param name 名称
     * @param material 材料
     * @param nameCode 标准代号
     * @return
     */
    List<Stock> selectByNameAndNameCodeAndMaterial(String name,String material,String nameCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据库存编号查询
     *
     * @param stockCode
     * @return com.cubicpark.mechanic.domain.dto.storage.Stock
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    Stock selectByStockCode(String stockCode);
}
