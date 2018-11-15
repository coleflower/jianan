package com.cubicpark.mechanic.domain.service.contract;

import java.math.BigDecimal;
import java.util.List;

import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 合同款项服务
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IContractFundService {
    
    /**
     * 
     * 功能描述: <br>
     * 新增合同款项
     *  启动责任链
     *  判断合同状态是否为有效时，如有效进行下一步，否则返回错误码退出。
     *  判断存在同合同下未提交的款项条目，如果没有进行下一步，否则返回错误码退出。
     *  判断信息状态是保存还是提交，如果是保存，执行保存方法返回错误码退出；如果为提交执行下一步。
     *  获取合同预付款比例，合同 款项状态
     *   1 如果预付款比例100%， 合同款项状态为非未付款，设置错误码退出，如果不是则执行2
     *   2 如果预付款比例100% 合同款项状态为未付款：是执行详细业务逻辑， 不是执行3
     *      业务逻辑：判断款项金额和合同标的是否一致且款项类型为尾款，如果一致提交款项状态并设置合同款项状态为全部结清，设置成功退出，不一致返回错误码退出
     *   3 如果预付款比例非100%， 合同款项状态为已尾款已结清，设置错误码退出，如果不是则执行4
     *   4 如果预付款比例非100%， 合同款项状态为未首付：是执行业务逻辑，不是执行5
     *      业务逻辑：款项类型为首期款且判断款项金额和合同标的乘以比例是否一致，符合条件则提交并设置合同款项状态为已首付待回款，设置成功退出，不一致返回错误码退出
     *   5 如果预付款比例非100%， 合同款项状态为已首付待回款，执行业务逻辑，否则执行6
     *      业务逻辑：款项类型为正常回款且所有款项金额累积不超过合同标的，符合条件则提交并设置合同款项为正常回款未结清，设置成功退出，如果不一致设置错误码退出
     *   6 如果预付款比例非100%， 合同款项状态为正常回款未结清，判断款项类型是否为正常回款 且所有款项金额累积不超过合同标的,提交款项信息设置成功信息返回，如果不一致执行7
     *   7 如果预付款比例非100%， 合同款项状态为正常回款未结清，款项类型为尾款 所有款项金额累积等于合同标的，设置合同款项为已尾款已结清，提交款项信息，设置成功信息，如果不对设置错误信息码并退出
     *
     * @param contractFund
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void addContractFund(ContractFundCheckDTO contractFundCheck) throws Exception;
    
    /**
     * 
     * 功能描述: <br>
     * 修改合同款项
     * 判断合同状态是否为有效时，如有效进行下一步，否则返回错误码退出。
     * 如果当前款项信息状态为保存，如有是保存行下一步，否则返回错误码退出
     * 判断信息状态是保存还是提交，如果是保存，执行保存方法返回错误码退出；如果为提交执行下一步。
     *  获取合同预付款比例，合同 款项状态
     *   1 如果预付款比例100%， 合同款项状态为非未付款，设置错误码退出，如果不是则执行2
     *   2 如果预付款比例100% 合同款项状态为未付款：是执行详细业务逻辑， 不是执行3
     *      业务逻辑：判断款项金额和合同标的是否一致且款项类型为尾款，如果一致提交款项状态并设置合同款项状态为全部结清，设置成功退出，不一致返回错误码退出
     *   3 如果预付款比例非100%， 合同款项状态为已尾款已结清，设置错误码退出，如果不是则执行4
     *   4 如果预付款比例非100%， 合同款项状态为未首付：是执行业务逻辑，不是执行5
     *      业务逻辑：款项类型为首期款且判断款项金额和合同标的乘以比例是否一致，符合条件则提交并设置合同款项状态为已首付待回款，设置成功退出，不一致返回错误码退出
     *   5 如果预付款比例非100%， 合同款项状态为已首付待回款，执行业务逻辑，否则执行6
     *      业务逻辑：款项类型为正常回款且所有款项金额累积不超过合同标的，符合条件则提交并设置合同款项为正常回款未结清，设置成功退出，如果不一致设置错误码退出
     *   6 如果预付款比例非100%， 合同款项状态为正常回款未结清，判断款项类型是否为正常回款 且所有款项金额累积不超过合同标的,提交款项信息设置成功信息返回，如果不一致执行7
     *   7 如果预付款比例非100%， 合同款项状态为正常回款未结清，款项类型为尾款 所有款项金额累积等于合同标的，设置合同款项为已尾款已结清，提交款项信息，设置成功信息，如果不对设置错误信息码并退出
     * 
     * @param contractFund
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void modifyContractFund(ContractFundCheckDTO contractFundCheck) throws Exception;
    
    /**
     * 
     * 功能描述: <br>
     * 增加款项ACTION
     *
     * @param contractFund
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String add(ContractFundDTO contractFund);
    
    /**
     * 
     * 功能描述: <br>
     * 修改款项ACTION
     *
     * @param contractFund
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String modify(ContractFundDTO contractFund);
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delContractFund(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractFundDTO> getContractFundByContractCode(String contractCode);
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ContractFundDTO getContractFundByID(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 获取该合同下未提交的款项信息
     *
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractFundDTO> getSaveInfoByContractCode(String contractCode);
    
    /**
     * 
     * 功能描述: <br>
     * 获取该合同下所有已支付的合计款项
     *
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    BigDecimal getContractSumPaidFund(String contractCode);
}
