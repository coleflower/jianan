package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 预付款比例非100%， 合同款项状态为已尾款已结清规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundAdvanceAndEndPayRule extends MessageBranchNode {

    @Override
    protected boolean makeDecision(Object paramObject) throws Exception {
        ContractFundCheckDTO contractFundCheck = (ContractFundCheckDTO) paramObject;
        String advanceRatio = contractFundCheck.getAdvanceRatio();
        String fundState = contractFundCheck.getFundState();

        // 如果预付款比例非100%， 合同款项状态为已尾款已结清，设置错误码退出，如果不是则执行下一步
        if (!Constants.ADVANCE_RATIO_100.equals(advanceRatio) && Constants.FUND_STATE_ENDPAY.equals(fundState)) {
            // 设置错误码退出
            contractFundCheck.setErrorCode(getMessageCode());
            return false;
        } else {
            return true;
        }
    }

}
