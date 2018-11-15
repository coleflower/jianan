package com.cubicpark.mechanic.domain.service.storage.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.dao.storage.IStockDAO;
import com.cubicpark.mechanic.domain.dto.storage.Rack;
import com.cubicpark.mechanic.domain.dto.storage.Stock;
import com.cubicpark.mechanic.domain.service.storage.IRackService;
import com.cubicpark.mechanic.domain.service.storage.IStockService;
import com.cubicpark.mechanic.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
@Service
public class StockServiceImpl extends ServiceImpl<IStockDAO, Stock> implements IStockService {

    @Autowired
    private IRackService rackService;

    @Override
    public boolean updateAddStock(Stock stock) {
        // 入库
        stock.setType(0);
        Rack rack = rackService.findByRackCode(stock.getRackCode());
        // 计算库存
        Integer result = Integer.valueOf(rack.getStock()) + Integer.valueOf(stock.getNumber());
        if (result > Integer.valueOf(rack.getSize())) {
            throw new ServiceException(CommonErrorCode.RACK_SIZE_OUT);
        }
        rack.setStock(String.valueOf(result));
        return super.insert(stock) && rackService.updateById(rack);
    }

    @Override
    public boolean updateDelStock(Stock stock) {
        // 出库
        stock.setType(1);
        Rack rack = rackService.findByRackCode(stock.getRackCode());
        // 计算库存
        Integer result = Integer.valueOf(rack.getStock()) - Integer.valueOf(stock.getNumber());
        if (result < 0) {
            throw new ServiceException(CommonErrorCode.RACK_SIZE_OUT);
        }
        rack.setStock(String.valueOf(result));
        return super.insert(stock) && rackService.updateById(rack);
    }

    @Override
    public List<Stock> selectAll() {
        EntityWrapper<Stock> wrapper = new EntityWrapper<>();
        return super.selectList(wrapper);
    }

    @Override
    public Stock selectByStockCode(String stockCode) {
        EntityWrapper<Stock> wrapper = new EntityWrapper<>();
        wrapper.eq("stock_code",stockCode);
        return super.selectOne(wrapper);
    }

    @Override
    public List<Stock> selectByNameAndNameCodeAndMaterial(String name,String material, String nameCode) {
        EntityWrapper<Stock> wrapper = new EntityWrapper<>();
        wrapper.eq("name",name).eq("material",material).eq("goods_code",nameCode);
        return super.selectList(wrapper);
    }
}

