package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import java.math.BigDecimal;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 预付款比例非100%， 合同款项状态为正常回款未结清，款项类型是为正常回款规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundAdvanceAndBackPayRule extends MessageBranchNode {
    private IContractFundService contractFundService;

    public IContractFundService getContractFundService() {
        return contractFundService;
    }

    public void setContractFundService(IContractFundService contractFundService) {
        this.contractFundService = contractFundService;
    }

    @Override
    protected boolean makeDecision(Object paramObject) throws Exception {
        ContractFundCheckDTO contractFundCheck = (ContractFundCheckDTO) paramObject;
        String advanceRatio = contractFundCheck.getAdvanceRatio();
        String fundState = contractFundCheck.getFundState();
        String total = contractFundCheck.getTotal();
        ContractFundDTO contractFund = contractFundCheck.getContractFund();

        // 如果预付款比例非100%， 合同款项状态为正常回款未结清，判断款项类型是否为正常回款 ，否则执行下一步
        if (!Constants.ADVANCE_RATIO_100.equals(advanceRatio) && Constants.FUND_STATE_BACKPAY.equals(fundState)
                && Constants.FUND_TYPE_BACKPAY.equals(contractFund.getFundType())) {

            BigDecimal totalFree = new BigDecimal(total);// 获取合同总价
            // 获取累积款项+回款款项
            BigDecimal allPay = contractFundService.getContractSumPaidFund(contractFund.getContractCode()).add(
                    new BigDecimal(contractFund.getFund()));

            // 款项类型为正常回款且所有款项金额累积不超过合同标的
            if (totalFree.compareTo(allPay) > 0) {
                contractFund.setInfoState(Constants.COMMIT);// 设置款项信息状态为提交

                // 提交款项信息
                if (contractFund.getId() == 0) {
                    contractFundService.add(contractFund);
                } else {
                    contractFundService.modify(contractFund);
                }
            } else {
                // 不一致返回错误码退出
                contractFundCheck.setErrorCode(getMessageCode());
            }

            return false;
        } else {
            return true;
        }
    }

}
