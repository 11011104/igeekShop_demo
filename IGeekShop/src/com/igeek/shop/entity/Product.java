package com.igeek.shop.entity;

import java.util.Date;

/**
 * 
* @ClassName: Product  
* @Description: 商品实体类
* @date 2017年12月13日 上午9:23:33    
* Company www.igeekhome.com
*
 */
public class Product {
	private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private String pimage;
	private Date pdate;
	private int is_hot;//0--非热门商品  1-热门商品
	private String pdesc;
	private int pflag;//是否上下架
	
	private Category category;//商品的类别,上层面向对象

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * @return the market_price
	 */
	public double getMarket_price() {
		return market_price;
	}

	/**
	 * @param market_price the market_price to set
	 */
	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	/**
	 * @return the shop_price
	 */
	public double getShop_price() {
		return shop_price;
	}

	/**
	 * @param shop_price the shop_price to set
	 */
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}

	/**
	 * @return the pimage
	 */
	public String getPimage() {
		return pimage;
	}

	/**
	 * @param pimage the pimage to set
	 */
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	/**
	 * @return the pdate
	 */
	public Date getPdate() {
		return pdate;
	}

	/**
	 * @param pdate the pdate to set
	 */
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	/**
	 * @return the is_hot
	 */
	public int getIs_hot() {
		return is_hot;
	}

	/**
	 * @param is_hot the is_hot to set
	 */
	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}

	/**
	 * @return the pdesc
	 */
	public String getPdesc() {
		return pdesc;
	}

	/**
	 * @param pdesc the pdesc to set
	 */
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	/**
	 * @return the pflag
	 */
	public int getPflag() {
		return pflag;
	}

	/**
	 * @param pflag the pflag to set
	 */
	public void setPflag(int pflag) {
		this.pflag = pflag;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
