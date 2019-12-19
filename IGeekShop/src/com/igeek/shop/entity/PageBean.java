package com.igeek.shop.entity;

import java.util.List;
/**
 * 
* @ClassName: PageBean  
* @Description: 分页辅助实体类
* @date 2017年12月14日 下午4:42:53    
* Company www.igeekhome.com
*  
* @param <T>
 */
public class PageBean<T> {
	private int currentPage;//当前页
	private int currentCount;//当前页 显示的条数 
	private int totalPage;//总页数
	private int totalCount;//总条数
	private List<T> list;//当前页显示的对象集合
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the currentCount
	 */
	public int getCurrentCount() {
		return currentCount;
	}
	/**
	 * @param currentCount the currentCount to set
	 */
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
