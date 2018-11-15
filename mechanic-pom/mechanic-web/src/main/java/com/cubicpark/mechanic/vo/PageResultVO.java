package com.cubicpark.mechanic.vo;

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
public class PageResultVO implements Serializable {

	private static final long serialVersionUID = 1L;
	// 总记录数
	private int total;
	// 当前页数
	private int pageNumber;
	// 每页条数
	private int pageSize;
	// 总页数
	private int pageCount;
    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }
    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }
    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }
    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * @return the pageCount
     */
    public int getPageCount() {
        return pageCount;
    }
    /**
     * @param pageCount the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
