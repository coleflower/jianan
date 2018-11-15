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
 * 预付款比例非100%， 合同款项状态为未首付规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundAdvanceAndNoFirstPayRule extends MessageBranchNode {
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

        // 如果预付款比例非100%， 合同款项状态为未首付，否则执行下一步
        if (!Constants.ADVANCE_RATIO_100.equals(advanceRatio) && Constants.FUND_STATE_NOFIRSTPAY.equals(fundState)) {

            BigDecimal totalFree = new BigDecimal(total);// 获取合同总价
            BigDecimal freeRation = new BigDecimal(Integer.valueOf(advanceRatio));// 预付比例 为整数 0到100之间
            BigDecimal firstFree = totalFree.multiply(freeRation).divide(new BigDecimal(100), 2,
                    BigDecimal.ROUND_HALF_UP);// 按照预付比例获取收款，精确2位4舍5入
            BigDecimal firstPay = new BigDecimal(contractFund.getFund());

            // 款项类型为首期款 且判断款项金额和合同标的乘以比例是否一致
            if (Constants.FUND_TYPE_FIRSTPAY.equals(contractFund.getFundType()) && (firstFree.compareTo(firstPay) == 0)) {
                contractFund.setInfoState(Constants.COMMIT);// 设置款项信息状态为提交

                // 提交款项信息
                String commitResult = null;
                if (contractFund.getId() == 0) {
                    commitResult = contractFundService.add(contractFund);
                } else {
                    commitResult = contractFundService.modify(contractFund);
                }

                // 修改合同信息 设置合同款项状态为已首付待回款
                String modifyResult = contractService.modifyContractFundState(contractFund.getContractCode(),
                        Constants.FUND_STATE_FIRSTPAY, null);

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
