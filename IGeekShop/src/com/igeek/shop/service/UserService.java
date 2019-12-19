package com.igeek.shop.service;

import java.sql.SQLException;

import com.igeek.shop.dao.UserDao;
import com.igeek.shop.entity.User;
/**
 * 
* @ClassName: UserService  
* @Description: �û�����Service��
* @date 2017��12��12�� ����4:19:39    
* Company www.igeekhome.com
*
 */
public class UserService {
	private UserDao dao = new UserDao();
	/**
	 * 
	* @Title: regist  
	* @Description: �û�ע�� 
	* @param user
	* @return
	 */
	public boolean regist(User user) {
		//ҵ���߼�
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
	* @Description: �û�����
	* @param activeCode
	* @return
	 */
	public boolean active(String activeCode) {
		//�����û���״̬
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
	* @Description: ��֤�û��Ƿ���� 
	* @param username
	* @return
	 */
	public boolean checkUsername(String username) {
		//����dao�ж��Ƿ����
		Long count = 0L;
		try {
			count = dao.checkUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count>0?true:false;
	}

}
