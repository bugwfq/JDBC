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
 * 编程题3 使用jdbc复制emp表数据到新表emp1（该表在执行代码时不存在）（20）
  不能直接执行sql (create table emp1 as select * from emp)
  必须通过ResultSet获取emp表数据写入emp1
 * @author Administrator
 *
 */
public class Thirdly {
	static Connection conn = null;
	static{
		//获取驱动类的全类名
		String driverClass = "oracle.jdbc.OracleDriver";
		//获取连接的url
		String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
		//获取连接的用户名
		String user = "scott";
		//获取连接的用户密码
		String password = "tiger";
		//加载驱动类
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取连接的对象
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		//需要执行的sql语句
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
		//创建emp1表
		createTable();
		//插入值
		addEmp1(list);
		
	}
	public static void createTable(){
		//建表语句
		String sql2 = "create table EMP1(empno NUMBER(4) ,ename VARCHAR2(10),job VARCHAR2(9),mgr NUMBER(4),hiredate DATE,sal NUMBER(7,2),comm NUMBER(7,2),deptno NUMBER(2),empdesc VARCHAR2(20))";
		try {
			//执行代码
			PreparedStatement ps = conn.prepareStatement(sql2);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addEmp1(List<String> list){
		String sql = "insert into emp values(?,?,?,?,?,?,?,?,?)";
		//获取一个连接
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
			//批量加入一些值
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
			//执行批处理的sql语句
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
