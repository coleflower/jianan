package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import java.math.BigDecimal;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.exception.ServiceException;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 预付款比例非100%， 合同款项状态为已首付待回款 规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundAdvanceAndFirstPayRule extends MessageBranchNode {
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
        ContractFundDTO contractFund = contractFundCheck.getContractFund();
        
        // 如果预付款比例非100%， 合同款项状态为已首付待回款，执行业务逻辑，否则执行下一步
        if (!Constants.ADVANCE_RATIO_100.equals(advanceRatio) && Constants.FUND_STATE_FIRSTPAY.equals(fundState)) {

            BigDecimal totalFree = new BigDecimal(total);// 获取合同总价
            // 获取累积款项+回款款项
            BigDecimal allPay = contractFundService.getContractSumPaidFund(contractFund.getContractCode()).add(
                    new BigDecimal(contractFund.getFund()));

            // 款项类型为正常回款且所有款项金额累积不超过合同标的
            if (Constants.FUND_TYPE_BACKPAY.equals(contractFund.getFundType()) && (totalFree.compareTo(allPay) > 0)) {
                contractFund.setInfoState(Constants.COMMIT);// 设置款项信息状态为提交

                // 提交款项信息
                String commitResult = null;
                if (contractFund.getId() == 0) {
                    commitResult = contractFundService.add(contractFund);
                } else {
                    commitResult = contractFundService.modify(contractFund);
                }

                // 修改合同信息 设置合同款项状态为正常回款未结清
                String modifyResult = contractService.modifyContractFundState(contractFund.getContractCode(),
                        Constants.FUND_STATE_BACKPAY, null);

                if (!"200".equals(commitResult) && !"200".equals(modifyResult)) {
                    contractFundCheck.setErrorCode(getMessageCode());
                    throw new ServiceException("提交款项信息或者修改合同信息失败！");
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
