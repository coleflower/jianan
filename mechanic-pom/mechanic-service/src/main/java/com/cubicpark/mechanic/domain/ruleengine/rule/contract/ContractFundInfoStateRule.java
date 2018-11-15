package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 款项信息状态规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundInfoStateRule extends MessageBranchNode {

    private IContractFundService contractFundService;

    public IContractFundService getContractFundService() {
        return contractFundService;
    }

    public void setContractFundService(IContractFundService contractFundService) {
        this.contractFundService = contractFundService;
    }

    @Override
    protected boolean makeDecision(Object paramObject) throws Exception {
        String currentInfoState = null;

        ContractFundCheckDTO contractFundCheck = (ContractFundCheckDTO) paramObject;
        ContractFundDTO contractFund = contractFundCheck.getContractFund();

        if (contractFund != null)
            currentInfoState = contractFund.getInfoState();

        /**
         * 如果是新增是款项信息状态默认为保持，根据提交动作来设置是否提交属性。
         * 如果操作此步骤前当前款项信息状态为保存，且当前操作为提交行下一步。否则更新
         *  判断信息状态是保存还是提交，如果是保存，执行保存方法返回错误码退出；如果为提交执行下一步。
         */
        if (Constants.SAVE.equals(currentInfoState) && contractFundCheck.isCommit()) {
            return true;
        } else if (Constants.SAVE.equals(currentInfoState) && !contractFundCheck.isCommit()) {
            // 新增还是更新 
            if (contractFund.getId() == 0) {
                contractFundService.add(contractFund);
            } else {
                contractFundService.modify(contractFund);
            }
            return false;
        } else {
            contractFundCheck.setErrorCode(getMessageCode());
            return false;
        }
    }

}
