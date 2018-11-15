package com.cubicpark.mechanic.domain.ruleengine.rule;

import com.firstji.mentha.domain.service.ruleengine.AbstractBranchNode;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 带错误码的规则节点抽象类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class MessageBranchNode extends AbstractBranchNode {

    private String messageCode;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }


}
