package JCMconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.mysql.jdbc.Driver;


/**
 * JDBC链接mysql数据库的三种链接方式
 * @author Administrator
 *
 */
public class ConnectionTest {
	/*
	 * 链接数据库通常有三个关键步骤
	 * 1.连接数据库的地址url
	 * 2.连接数据库的用户名user
	 * 3.连接数据库的密码password
	 */
	//mysql链接数据库的url  jdbc:mysql://localhost:3306/test
	private String url="jdbc:mysql://localhost:3306/test";
	          //jdbc协议:数据库协议://主机地址:端口/数据库名或sid
	//连接数据库的用户名
	private String user ="root";
	//链接数据库的密码
	private String password = "root";
	
	
	
	/**
	 * 第一种连接的方式
	 * 创建驱动程序类对象 new com.mysql.jdbc.Driver();
	 * 使用 connect(String,Properties)方法返回一个连接的connection
	 */
	@Test
	private void connection1(){

		try {
			//创建驱动程序类对象
			Driver driver = new Driver();
			//将用户名和密码封装到一个Properties对象中
			Properties pro = new Properties();
			pro.setProperty("user", user);
			pro.setProperty("password", password);
			//连接数据库返回一个Connection对象
			Connection conn =  driver.connect(url,pro);
			System.out.println(conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	/**
	 * 第二种连接的方式
	 * 创建驱动程序类对象 new com.mysql.jdbc.Driver();
	 * 使用DriverManager
	 */
	private void connection2(){
		
		try {
			//创建一个驱动程序类对象
			Driver driver = new com.mysql.jdbc.Driver();
			//使用DriverManager注册驱动程序
			DriverManager.registerDriver(driver);
			//使用DriverManager连接数据库返回一个Connection对象
			Connection conn = DriverManager.getConnection(url,user,password);
			System.out.println(conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	/**
	 * 重点
	 * 第三种（常用）的连接方式
	 * 利用反射加载类new com.mysql.jdbc.Driver
	 */
	private void connection3(){
		
		try {
			//利用反射加载Driver类
			Class.forName("com.mysql.jdbc.Driver");
			//获取链接对象
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
