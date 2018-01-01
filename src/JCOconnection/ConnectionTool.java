package JCOconnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTool {
	//创建一个Properties对象用来获取配置文件的信息
	static Properties pro = new Properties();
	static {
		//获取当前类的文件目录下的jdbcO.properties文件并返回一个流
		InputStream is = ConnectionTool.class.getResourceAsStream("\\jdbcO.properties");
		try {
			//将配置文件获取到pro对象中
			pro.load(is);
		} catch (IOException e) {
			
		}
	}
	/**
	 * 返回一个链接oracle的Connection对象
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		//获取驱动类的全类名
		String driverClass = pro.getProperty("driverClass");
		//获取连接的url
		String url = pro.getProperty("url");
		//获取连接的用户名
		String user = pro.getProperty("user");
		//获取连接的用户密码
		String password = pro.getProperty("password");
		//加载驱动类
		Class.forName(driverClass);
		//获取连接的对象
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
		return conn;
	}
}
