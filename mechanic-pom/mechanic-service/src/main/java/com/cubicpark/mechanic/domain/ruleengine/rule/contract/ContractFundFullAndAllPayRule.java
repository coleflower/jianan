package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 预付款比例100%， 合同款项状态为非未付款规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundFullAndAllPayRule extends MessageBranchNode {

    @Override
    protected boolean makeDecision(Object paramObject) throws Exception {

        ContractFundCheckDTO contractFundCheck = (ContractFundCheckDTO) paramObject;
        String advanceRatio = contractFundCheck.getAdvanceRatio();
        String fundState = contractFundCheck.getFundState();

        // 如果预付款比例100%， 合同款项状态为非未付款，设置错误码退出，如果不是则执行下一步
        if (Constants.ADVANCE_RATIO_100.equals(advanceRatio) && !Constants.FUND_STATE_NOPAY.equals(fundState)) {
            contractFundCheck.setErrorCode(getMessageCode());
            return false;
        } else {
            return true;
        }
    }

}
