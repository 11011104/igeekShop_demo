package com.igeek.shop.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.igeek.shop.entity.User;

public class UserPowerFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//验证用户是否登录状态
		//已登录---放行
		//未登录---跳转到登录页面
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		
		HttpSession session = req.getSession();

		// 验证用户是否登录
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 未登录状态
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}
		
		
		//放行
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
