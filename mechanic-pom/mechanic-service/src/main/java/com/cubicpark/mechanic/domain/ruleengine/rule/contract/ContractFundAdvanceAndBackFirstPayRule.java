package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 预付款比例非100%， 合同款项状态为正常回款未结清，款项类型为正常回款或者尾款规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundAdvanceAndBackFirstPayRule extends MessageBranchNode {

    @Override
    protected boolean makeDecision(Object paramObject) throws Exception {
        ContractFundCheckDTO contractFundCheck = (ContractFundCheckDTO) paramObject;
        String advanceRatio = contractFundCheck.getAdvanceRatio();
        String fundState = contractFundCheck.getFundState();
        ContractFundDTO contractFund = contractFundCheck.getContractFund();

        // 如果预付款比例非100%， 合同款项状态为正常回款未结清，判断款项类型不是首付款 ，否则执行下一步
        if (!Constants.ADVANCE_RATIO_100.equals(advanceRatio) && Constants.FUND_STATE_BACKPAY.equals(fundState)
                && Constants.FUND_TYPE_FIRSTPAY.equals(contractFund.getFundType())) {
            // 不一致返回错误码退出
            contractFundCheck.setErrorCode(getMessageCode());
            return false;
        } else {
            return true;
        }
    }

}
