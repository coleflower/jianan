package com.cubicpark.mechanic.exception;

import java.io.Serializable;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SystemException extends Exception implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1761166435556723889L;

    /**
     * 获取基本异常信息
     * 
     */
    public SystemException() {
        super();
    }

    /**
     * 获取基本异常信息
     * 
     * @param message
     */
    public SystemException(String message) {
        super(message);
        setErrorMessage(message);
    }

    /**
     * 获取基本异常信息
     * 
     * @param message
     * @param t
     */
    public SystemException(String message, Exception t) {
        super(message, t);
        setErrorMessage(message);
    }

    /**
     * 获取基本异常信息
     * 
     * @param t
     */
    public SystemException(Exception t) {
        super(t);
        setErrorMessage(t.getMessage());
    }

    /**
     * 获取基本异常信息
     * 
     * @param errorId
     * @param message
     */
    public SystemException(String errorId, String message) {
        super(message, new Exception(errorId));
        setErrorId(errorId);
        setErrorMessage(message);
        System.out.println(errorId + "++++++++++++++++++++" + message);
    }

    private String errorId = "-1";
    private String errorMessage;

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
