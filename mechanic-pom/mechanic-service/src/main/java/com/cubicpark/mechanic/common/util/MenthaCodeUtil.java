package com.cubicpark.mechanic.common.util;

import com.cubicpark.mechanic.dao.base.ISeqDAO;
import com.firstji.mentha.common.utils.StringUtil;

public final class MenthaCodeUtil {
    
    private static ISeqDAO seqDAO;
    
    public void setSeqDAO(ISeqDAO seqDAO) {
        this.seqDAO = seqDAO;
    }

    public static String generateMenthaCode(String seqName, int codeLength) {
        StringBuffer code = new StringBuffer();
        code.append(seqDAO.getSeqHead(seqName)).append(
                StringUtil.fillZero(seqDAO.getNextSeqValue(seqName).toString(), codeLength));
        return code.toString();
    }

}
