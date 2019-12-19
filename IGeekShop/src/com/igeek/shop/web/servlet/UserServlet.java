package com.igeek.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.igeek.common.utils.CommonUtils;
import com.igeek.common.utils.MailUtils;
import com.igeek.shop.entity.User;
import com.igeek.shop.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/*
	 * protected void doGet(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { // 處理用戶的請求 String method
	 * = request.getParameter("method"); if ("active".equals(method)) {
	 * active(request, response); } else if ("checkUsername".equals(method)) {
	 * checkUsername(request, response); } else if ("regist".equals(method)) {
	 * regist(request, response); } }
	 * 
	 *//**
		 * @see HttpServlet#doPost(HttpServletRequest request,
		 *      HttpServletResponse response)
		 *//*
		 * protected void doPost(HttpServletRequest request, HttpServletResponse
		 * response) throws ServletException, IOException { // TODO
		 * Auto-generated method stub doGet(request, response); }
		 */
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取激活码
		String activeCode = request.getParameter("activeCode");
		// 2.激活用户
		UserService service = new UserService();
		boolean isSuccess = service.active(activeCode);
		if (isSuccess) {
			// 跳转到登录页面
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			// 激活失败
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}

	public void checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取参数
		String username = request.getParameter("username");

		// service验证是否存在
		UserService service = new UserService();
		boolean isExist = service.checkUsername(username);
		String json = "{\"isExist\":" + isExist + "}";
		response.getWriter().write(json);

	}

	public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 0.解决中文乱码问题
		request.setCharacterEncoding("utf-8");

		User user = new User();
		// 1.获取表单中的数据
		try {

			// 手动将String转成Date类型
			ConvertUtils.register(new Converter() {
				/**
				 * 
				 * @Title: convert
				 * @Description: 实现类型转换啊
				 * @param clazz：目标的数据类型
				 * @param value:要转换的数据
				 * @return
				 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
				 *      java.lang.Object)
				 */
				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					Date desc = null;
					try {
						desc = sf.parse((String) value);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return desc;
				}
			}, Date.class);

			//// 2.组装成User实体对象
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// uid,state(默认就是0),code（激活码）
		// UUID生成不重复的字符串
		user.setUid(CommonUtils.getUUID());
		String activeCode = CommonUtils.getUUID();
		user.setCode(activeCode);
		// 3.调用service的注册的方式

		UserService service = new UserService();
		boolean isSuccess = service.regist(user);
		if (isSuccess) {
			String emailMsg = "恭喜您，注册成功！请点击下面的激活码进行账户激活。<br>"
					+ "<a href='http://localhost:8080/IGeekShop/user?method=active&activeCode=" + activeCode
					+ "'>http://localhost:8080/IGeekShop/user?method=active&activeCode=" + activeCode + "</a>";
			// 发送邮件，激活用户
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 跳转到成功页面
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		} else {
			// 失败叶面
			// 跳转到成功页面
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}

	}

	// 用户登录
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// 获得输入的用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// 对密码进行加密
		// password = MD5Utils.md5(password);

		// 将用户名和密码传递给service层
		UserService service = new UserService();
		User user = null;
		try {
			user = service.login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 判断用户是否登录成功 user是否是null
		if (user != null) {
			// 登录成功
			// ***************判断用户是否勾选了自动登录*****************
			String autoLogin = request.getParameter("autoLogin");
			if ("autoLogin".equals(autoLogin)) {
				// 要自动登录
				// 创建存储用户名的cookie
				Cookie cookie_username = new Cookie("cookie_username", user.getUsername());
				cookie_username.setMaxAge(10 * 60);
				// 创建存储密码的cookie
				Cookie cookie_password = new Cookie("cookie_password", user.getPassword());
				cookie_password.setMaxAge(10 * 60);

				response.addCookie(cookie_username);
				response.addCookie(cookie_password);

			}

			// ***************************************************
			// 将user对象存到session中
			session.setAttribute("user", user);

			// 重定向到首页
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {
			request.setAttribute("loginError", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	/**
	 * 
	* @Title: logout  
	* @Description: 用户退出
	* @param request
	* @param response
	* @throws ServletException
	* @throws IOException
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//从session中移除用户
		session.removeAttribute("user");
		
		//从cookie中页删除干净
		// 要自动登录
		// 创建存储用户名的cookie
		Cookie cookie_username = new Cookie("cookie_username", "");
		cookie_username.setMaxAge(0);
		// 创建存储密码的cookie
		Cookie cookie_password = new Cookie("cookie_password", "");
		cookie_password.setMaxAge(0);

		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		
		
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		
	}

}
