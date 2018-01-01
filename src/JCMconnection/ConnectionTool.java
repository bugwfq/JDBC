package JCMconnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class ConnectionTool {
	//获取配置文件中的信息
	static Properties pro = new Properties();
	static{
		//指定properties文件的路径并返回一个读入流
		InputStream is = ConnectionTool.class.getResourceAsStream("\\jdbcM.properties");
		try {
			//加载文件到pro中
			pro.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/**
	 * 根据配置文件中的信息返回一个连接对象对象
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		//获取url
		String url = pro.getProperty("url");
		//获取user
		String user = pro.getProperty("user");
		//获取password
		String password = pro.getProperty("password");
		//获取driver的全类名
		String driver = pro.getProperty("driverClass");
	
		//利用反射加载Driver类
		Class.forName(driver);
		//获取连接对象并返回
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
		
	}
}
