package oracleAndJedis.buffer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDispose {
	static Connection conn = null;
	static{
		//获取驱动类的全类�?
		String driverClass = "oracle.jdbc.OracleDriver";
		//获取连接的url
		String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
		//获取连接的用户名
		String user = "scott";
		//获取连接的用户密�?
		String password = "tiger";
		//加载驱动�?
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取连接的对�?
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Emp oracleInput(String key){
		//查询语句
		String sql = "select * from emp";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				//将所有的员工信息拼接
				String value = rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+","+rs.getString(5)+","+rs.getString(6)+","+rs.getString(7)+","+rs.getString(8)+","+rs.getString(9);
				//依次切分
				String[] arr = value.split(",");
				//判断编号是否存在
				if(arr[0].equals(key)){
					//获取个emp对象
					Emp user = returnEmp(arr);
					return user;
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 返回�?个对�?
	 * @param arr
	 * @return
	 */
	public Emp returnEmp(String[] arr){
		//将信息封装为对象
		Emp u =new Emp(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8]);
		return u;
	}
}
