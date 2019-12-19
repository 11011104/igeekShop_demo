package com.igeek.shop.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igeek.shop.service.UserService;
/**
 * 
* @ClassName: ActiveServlet  
* @Description: 激活用户的web操作层
* @date 2017年12月12日 下午4:20:34    
* Company www.igeekhome.com
*
 */
public class ActiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	* @Title: doGet  
	* @Description: 账户激活
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	* @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取激活码
		String activeCode = request.getParameter("activeCode");
		//2.激活用户
		UserService service = new 	UserService();
		boolean isSuccess = service.active(activeCode);
		if(isSuccess)
		{
			//跳转到登录页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}else{
			//激活失败
			response.sendRedirect(request.getContextPath()+"/error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
