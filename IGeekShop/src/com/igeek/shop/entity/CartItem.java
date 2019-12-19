package com.igeek.shop.entity;
/**
 * 
* @ClassName: CartItem  
* @Description: 购物项
* @date 2017年12月18日 下午2:50:36    
* Company www.igeekhome.com
*
 */
public class CartItem {
	private Product product;
	//数量
	private int buyNum;
	//小计
	private double subTotal;
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the buyNum
	 */
	public int getBuyNum() {
		return buyNum;
	}
	/**
	 * @param buyNum the buyNum to set
	 */
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	/**
	 * @return the subTotal
	 */
	public double getSubTotal() {
		return subTotal;
	}
	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public CartItem(Product product, int buyNum, double subTotal) {
		super();
		this.product = product;
		this.buyNum = buyNum;
		this.subTotal = subTotal;
	}
	public CartItem() {
		super();
	}
	
	
}
