package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import org.apache.commons.lang.StringUtils;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.exception.ServiceException;

/***
 * 
 * 〈一句话功能简述〉<br>
 * 如果预付款比例100% 合同款项状态为未付款规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundFullAndNoPayRule extends MessageBranchNode {
    private IContractService contractService;
    private IContractFundService contractFundService;

    public IContractService getContractService() {
        return contractService;
    }

    public void setContractService(IContractService contractService) {
        this.contractService = contractService;
    }

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
        String settleDate = contractFundCheck.getSettleDate();
        ContractFundDTO contractFund = contractFundCheck.getContractFund();

        // 如果预付款比例100% 合同款项状态为未付款：是执行详细业务逻辑， 不是执行下一步
        if (Constants.ADVANCE_RATIO_100.equals(advanceRatio) && Constants.FUND_STATE_NOPAY.equals(fundState)) {
            // 判断款项金额和合同标的是否一致，款项类型为尾款且结清日期不为空
            if (total.equals(contractFund.getFund()) && Constants.FUND_TYPE_ENDPAY.equals(contractFund.getFundType())
                    && StringUtils.isNotBlank(settleDate)) {
                contractFund.setInfoState(Constants.COMMIT);// 设置款项信息状态为提交

                // 提交款项信息
                String commitResult = null;
                if (contractFund.getId() == 0) {
                    commitResult = contractFundService.add(contractFund);
                } else {
                    commitResult = contractFundService.modify(contractFund);
                }
                // 修改合同信息 设置合同款项状态为全部结清 设置合同结清日期
                String modifyResult = contractService.modifyContractFundState(contractFund.getContractCode(), Constants.FUND_STATE_ALLPAY,
                        settleDate);

                if (!"200".equals(commitResult) && !"200".equals(modifyResult)) {
                    contractFundCheck.setErrorCode(getMessageCode());
                    throw new ServiceException("提交款项信息或者修改合同信息失败！");
                }
            } else {
                // 设置错误码退出
                contractFundCheck.setErrorCode(getMessageCode());
            }

            return false;
        } else {
            return true;
        }

    }

}
