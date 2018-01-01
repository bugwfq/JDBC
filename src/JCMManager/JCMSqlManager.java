package JCMManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import JCMconnection.ConnectionTool;

/**
 * java�������ݿ�Ա�ִ��dml���
 * @author Administrator
 *
 */
public class JCMSqlManager {
	//@Test
	public void select(){
		//��ȡ����
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//ִ��sql����ȡ��ѯ�Ľ����
		String sql = "select * from sc";
		Statement sta = null;
		try {
			//��ȡ��ѯ�Ľ��
			sta = conn.createStatement();
			//ͨ��next����ÿһ������
			ResultSet res = sta.executeQuery(sql);
			while(res.next()){
				int id = res.getInt("s#");
				int course = res.getInt("c#");
				int score = res.getInt("score");
				System.out.println(id+"--"+course+"--"+score);
			}
			res.close();
			sta.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	//@Test
	public void delete(){
		//��ȡ����
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Ҫִ�е�sql���
		String sql = "delete from sc where `s#` = 8";
		Statement sta = null;
		try {
			sta = conn.createStatement();
			//ͨ��next����ÿһ������
			sta.executeUpdate(sql);
			sta.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
	@Test
	public void insert(){
		//��ȡ����
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//ִ��sql����ȡ��ѯ�Ľ����
		String sql = "insert into sc values(8,1,99)";
		Statement sta = null;
		try {
			sta = conn.createStatement();
			//ͨ��next����ÿһ������
			sta.executeUpdate(sql);
			sta.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
	}
	//@Test
	public void update(){
		//��ȡ����
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		//ִ��sql����ȡ��ѯ�Ľ����
		String sql = "update sc set score=10 where `s#`=8";
		Statement sta = null;
		try {
			sta = conn.createStatement();
			//ͨ��next����ÿһ������
			sta.executeUpdate(sql);
			sta.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
	}
}
