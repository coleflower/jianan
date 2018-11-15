package com.cubicpark.mechanic.domain.ruleengine.engine;

import com.firstji.mentha.domain.service.ruleengine.AbstractRuleEngine;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 规则引擎实现类
 * 如果没有特殊要求此类可以做为公共规则引擎
 * 通过SPRING 单例模式和BEANFACTORY生成不同ID的规则引擎
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RuleEngine extends AbstractRuleEngine {

    @Override
    public final void processRequest(final Object paramObject) throws Exception {
        getFirstStep().execute(paramObject);
    }

}
