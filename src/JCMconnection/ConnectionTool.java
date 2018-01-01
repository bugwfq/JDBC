package JCMconnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class ConnectionTool {
	//��ȡ�����ļ��е���Ϣ
	static Properties pro = new Properties();
	static{
		//ָ��properties�ļ���·��������һ��������
		InputStream is = ConnectionTool.class.getResourceAsStream("\\jdbcM.properties");
		try {
			//�����ļ���pro��
			pro.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/**
	 * ���������ļ��е���Ϣ����һ�����Ӷ������
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		//��ȡurl
		String url = pro.getProperty("url");
		//��ȡuser
		String user = pro.getProperty("user");
		//��ȡpassword
		String password = pro.getProperty("password");
		//��ȡdriver��ȫ����
		String driver = pro.getProperty("driverClass");
	
		//���÷������Driver��
		Class.forName(driver);
		//��ȡ���Ӷ��󲢷���
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
		
	}
}
