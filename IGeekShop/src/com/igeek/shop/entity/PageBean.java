package com.igeek.shop.entity;

import java.util.List;
/**
 * 
* @ClassName: PageBean  
* @Description: ��ҳ����ʵ����
* @date 2017��12��14�� ����4:42:53    
* Company www.igeekhome.com
*  
* @param <T>
 */
public class PageBean<T> {
	private int currentPage;//��ǰҳ
	private int currentCount;//��ǰҳ ��ʾ������ 
	private int totalPage;//��ҳ��
	private int totalCount;//������
	private List<T> list;//��ǰҳ��ʾ�Ķ��󼯺�
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
