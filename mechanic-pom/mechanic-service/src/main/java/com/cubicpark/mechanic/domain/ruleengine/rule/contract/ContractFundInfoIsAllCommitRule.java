package com.cubicpark.mechanic.domain.ruleengine.rule.contract;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.rule.MessageBranchNode;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 合同下所有款项条目信息是否提交检测规则
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundInfoIsAllCommitRule extends MessageBranchNode {

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
        String contractCode = contractFundCheck.getContractCode();

        // 获取当前合同下信息状态没有提交（保存）状态的款项信息
        List<ContractFundDTO> isNotCommitInfoList = contractFundService.getSaveInfoByContractCode(contractCode);

        // 判断存在同合同下未提交的款项条目，如果没有进行下一步，否则返回错误码退出。
        if (isNotCommitInfoList == null || isNotCommitInfoList.size() == 0) {
            return true;
        } else {
            contractFundCheck.setErrorCode(getMessageCode());
            return false;
        }
    }

}
