package com.igeek.common.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
* @ClassName: DataSourceUtils  
* @Description: 数据操作辅助类
* @date 2017年12月12日 下午4:16:24    
* Company www.igeekhome.com
*
 */
public class DataSourceUtils {

	private static DataSource dataSource = new ComboPooledDataSource();

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	/**
	 * 
	* @Title: getDataSource  
	* @Description:获取数据源
	* @return
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 
	* @Title: getConnection  
	* @Description: 获取链接对象
	* @return
	* @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		Connection con = tl.get();
		if (con == null) {
			con = dataSource.getConnection();
			tl.set(con);
		}
		return con;
	}

	// 开启事务
	public static void startTransaction() throws SQLException {
		Connection con = getConnection();
		if (con != null) {
			con.setAutoCommit(false);
		}
	}

	// 事务回滚
	public static void rollback() throws SQLException {
		Connection con = getConnection();
		if (con != null) {
			con.rollback();
		}
	}

	// 提交并且 关闭资源及从ThreadLocall中释放
	public static void commitAndRelease() throws SQLException {
		Connection con = getConnection();
		if (con != null) {
			con.commit(); // 事务提交
			con.close();// 关闭资源
			tl.remove();// 从线程绑定中移除
		}
	}

	// 关闭资源方法
	public static void closeConnection() throws SQLException {
		Connection con = getConnection();
		if (con != null) {
			con.close();
		}
	}

	public static void closeStatement(Statement st) throws SQLException {
		if (st != null) {
			st.close();
		}
	}

	public static void closeResultSet(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

}
