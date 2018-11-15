package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import org.apache.commons.lang.StringUtils;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 合同状态有效性检测规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractStateIsEffectiveRule extends MessageBranchNode {

    @Override
    protected boolean makeDecision(Object paramObject) throws Exception {
        ContractFundCheckDTO contractFundCheck = (ContractFundCheckDTO) paramObject;
        String contractState = contractFundCheck.getContractState();

        // 判断合同状态是否为有效时，如有效进行下一步，否则返回错误码退出。
        if (StringUtils.isNotBlank(contractState) && contractState.equals(Constants.CONTRACT_STATE_EFFECTIVE)) {
            return true;
        } else {
            contractFundCheck.setErrorCode(getMessageCode());
            return false;
        }
    }

}
