package com.igeek.shop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.igeek.common.utils.DataSourceUtils;
import com.igeek.shop.entity.User;
/**
 * 
* @ClassName: UserDao  
* @Description: �û��־ò������
* @date 2017��12��12�� ����4:18:44    
* Company www.igeekhome.com
*
 */
public class UserDao {
	/**
	 * 
	* @Title: regist  
	* @Description:�����û�
	* @param user
	* @return
	* @throws SQLException
	 */
	public int regist(User user) throws SQLException {
		//DButils
		
		QueryRunner  runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int row  = runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		return row;
	}
	/**
	 * 
	* @Title: active  
	* @Description:�����û�����״̬  
	* @param activeCode
	* @throws SQLException
	 */
	public void active(String activeCode) throws SQLException {
		QueryRunner runner =  new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state = 1 where code = ?" ;
		runner.update(sql, activeCode);
		
	}
	/**
	 * 
	* @Title: checkUsername  
	* @Description: �����û����ж��û��Ƿ���� 
	* @param username
	* @return
	* @throws SQLException
	 */
	public Long checkUsername(String username) throws SQLException {
		QueryRunner runner =  new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select count(*) from user where username = ?" ;
		Long count = (Long)runner.query(sql, new ScalarHandler(), username);
		return count;
	}

}
