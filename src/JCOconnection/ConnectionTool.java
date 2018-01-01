package JCOconnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTool {
	//����һ��Properties����������ȡ�����ļ�����Ϣ
	static Properties pro = new Properties();
	static {
		//��ȡ��ǰ����ļ�Ŀ¼�µ�jdbcO.properties�ļ�������һ����
		InputStream is = ConnectionTool.class.getResourceAsStream("\\jdbcO.properties");
		try {
			//�������ļ���ȡ��pro������
			pro.load(is);
		} catch (IOException e) {
			
		}
	}
	/**
	 * ����һ������oracle��Connection����
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		//��ȡ�������ȫ����
		String driverClass = pro.getProperty("driverClass");
		//��ȡ���ӵ�url
		String url = pro.getProperty("url");
		//��ȡ���ӵ��û���
		String user = pro.getProperty("user");
		//��ȡ���ӵ��û�����
		String password = pro.getProperty("password");
		//����������
		Class.forName(driverClass);
		//��ȡ���ӵĶ���
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
		return conn;
	}
}
