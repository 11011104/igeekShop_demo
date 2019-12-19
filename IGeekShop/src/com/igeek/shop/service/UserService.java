package com.igeek.shop.service;

import java.sql.SQLException;

import com.igeek.shop.dao.UserDao;
import com.igeek.shop.entity.User;
/**
 * 
* @ClassName: UserService  
* @Description: 用户操作Service层
* @date 2017年12月12日 下午4:19:39    
* Company www.igeekhome.com
*
 */
public class UserService {
	private UserDao dao = new UserDao();
	/**
	 * 
	* @Title: regist  
	* @Description: 用户注册 
	* @param user
	* @return
	 */
	public boolean regist(User user) {
		//业务逻辑
		int row = 0;
		try {
			 row = dao.regist(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row>0?true:false;
	}
	/**
	 * 
	* @Title: active  
	* @Description: 用户激活
	* @param activeCode
	* @return
	 */
	public boolean active(String activeCode) {
		//更新用户的状态
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	/**
	 * 
	* @Title: checkUsername  
	* @Description: 验证用户是否存在 
	* @param username
	* @return
	 */
	public boolean checkUsername(String username) {
		//调用dao判断是否存在
		Long count = 0L;
		try {
			count = dao.checkUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count>0?true:false;
	}
	//用户登录的方法
		public User login(String username, String password) throws SQLException {
			UserDao dao = new UserDao();
			return dao.login(username,password);
		}

}
