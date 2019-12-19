package com.igeek.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			// 获取用户请求的方法名
			String method = request.getParameter("method");
			// this---代表的是子类 product/user
			Class clazz = this.getClass();
			// 使用反射机制获取方法对象
			Method methodObj = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			// 使用反射機制调用方法
			methodObj.invoke(this, request,response);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		// 调用该方法

	}

}
