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
* @Description: �����û���web������
* @date 2017��12��12�� ����4:20:34    
* Company www.igeekhome.com
*
 */
public class ActiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	* @Title: doGet  
	* @Description: �˻�����
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	* @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ������
		String activeCode = request.getParameter("activeCode");
		//2.�����û�
		UserService service = new 	UserService();
		boolean isSuccess = service.active(activeCode);
		if(isSuccess)
		{
			//��ת����¼ҳ��
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}else{
			//����ʧ��
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
