package oracleAndJedis;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import JCOconnection.ConnectionTool;

/**
 * 
 * �����3 ʹ��jdbc����emp�����ݵ��±�emp1���ñ���ִ�д���ʱ�����ڣ���20��
  ����ֱ��ִ��sql (create table emp1 as select * from emp)
  ����ͨ��ResultSet��ȡemp������д��emp1
 * @author Administrator
 *
 */
public class Thirdly {
	static Connection conn = null;
	static{
		//��ȡ�������ȫ����
		String driverClass = "oracle.jdbc.OracleDriver";
		//��ȡ���ӵ�url
		String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
		//��ȡ���ӵ��û���
		String user = "scott";
		//��ȡ���ӵ��û�����
		String password = "tiger";
		//����������
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ȡ���ӵĶ���
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		//��Ҫִ�е�sql���
		String sql = "select * from emp";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String value = rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+","+rs.getString(5)+","+rs.getString(6)+","+rs.getString(7)+","+rs.getString(8)+","+rs.getString(9);
				list.add(value);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//����emp1��
		createTable();
		//����ֵ
		addEmp1(list);
		
	}
	public static void createTable(){
		//�������
		String sql2 = "create table EMP1(empno NUMBER(4) ,ename VARCHAR2(10),job VARCHAR2(9),mgr NUMBER(4),hiredate DATE,sal NUMBER(7,2),comm NUMBER(7,2),deptno NUMBER(2),empdesc VARCHAR2(20))";
		try {
			//ִ�д���
			PreparedStatement ps = conn.prepareStatement(sql2);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addEmp1(List<String> list){
		String sql = "insert into emp values(?,?,?,?,?,?,?,?,?)";
		//��ȡһ������
		Connection conn;
		PreparedStatement ps = null;
		try {
			conn = ConnectionTool.getConnection();
			ps = conn.prepareStatement(sql);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//��������һЩֵ
			for (String string : list) {
				String[] values = string.split(",");
				ps.setInt(1, Integer.parseInt(values[0]));
				ps.setString(2, values[1]);
				ps.setString(3, values[2]);
				ps.setInt(4, Integer.parseInt(values[3]));
				ps.setTimestamp(5, Timestamp.valueOf(values[4]));
				ps.setDouble(6, Double.parseDouble(values[5]));
				ps.setDouble(7, Double.parseDouble(values[6]));
				ps.setInt(8, Integer.parseInt(values[7]));
				ps.setString(9, values[8]);
				ps.addBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			//ִ���������sql���
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
