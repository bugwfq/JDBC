package JCMconnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.mysql.jdbc.Driver;


/**
 * JDBC����mysql���ݿ���������ӷ�ʽ
 * @author Administrator
 *
 */
public class ConnectionTest {
	/*
	 * �������ݿ�ͨ���������ؼ�����
	 * 1.�������ݿ�ĵ�ַurl
	 * 2.�������ݿ���û���user
	 * 3.�������ݿ������password
	 */
	//mysql�������ݿ��url  jdbc:mysql://localhost:3306/test
	private String url="jdbc:mysql://localhost:3306/test";
	          //jdbcЭ��:���ݿ�Э��://������ַ:�˿�/���ݿ�����sid
	//�������ݿ���û���
	private String user ="root";
	//�������ݿ������
	private String password = "root";
	
	
	
	/**
	 * ��һ�����ӵķ�ʽ
	 * ����������������� new com.mysql.jdbc.Driver();
	 * ʹ�� connect(String,Properties)��������һ�����ӵ�connection
	 */
	@Test
	private void connection1(){

		try {
			//�����������������
			Driver driver = new Driver();
			//���û����������װ��һ��Properties������
			Properties pro = new Properties();
			pro.setProperty("user", user);
			pro.setProperty("password", password);
			//�������ݿⷵ��һ��Connection����
			Connection conn =  driver.connect(url,pro);
			System.out.println(conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	/**
	 * �ڶ������ӵķ�ʽ
	 * ����������������� new com.mysql.jdbc.Driver();
	 * ʹ��DriverManager
	 */
	private void connection2(){
		
		try {
			//����һ���������������
			Driver driver = new com.mysql.jdbc.Driver();
			//ʹ��DriverManagerע����������
			DriverManager.registerDriver(driver);
			//ʹ��DriverManager�������ݿⷵ��һ��Connection����
			Connection conn = DriverManager.getConnection(url,user,password);
			System.out.println(conn);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	/**
	 * �ص�
	 * �����֣����ã������ӷ�ʽ
	 * ���÷��������new com.mysql.jdbc.Driver
	 */
	private void connection3(){
		
		try {
			//���÷������Driver��
			Class.forName("com.mysql.jdbc.Driver");
			//��ȡ���Ӷ���
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
