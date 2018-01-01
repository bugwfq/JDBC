package JCMManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import JCMconnection.ConnectionTool;

/**
 * java链接数据库对表执行dml语句
 * @author Administrator
 *
 */
public class JCMSqlManager {
	//@Test
	public void select(){
		//获取连接
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//执行sql语句获取查询的结果集
		String sql = "select * from sc";
		Statement sta = null;
		try {
			//获取查询的结果
			sta = conn.createStatement();
			//通过next遍历每一行数据
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
		//获取连接
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//要执行的sql语句
		String sql = "delete from sc where `s#` = 8";
		Statement sta = null;
		try {
			sta = conn.createStatement();
			//通过next遍历每一行数据
			sta.executeUpdate(sql);
			sta.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
	@Test
	public void insert(){
		//获取连接
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//执行sql语句获取查询的结果集
		String sql = "insert into sc values(8,1,99)";
		Statement sta = null;
		try {
			sta = conn.createStatement();
			//通过next遍历每一行数据
			sta.executeUpdate(sql);
			sta.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
	}
	//@Test
	public void update(){
		//获取连接
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		//执行sql语句获取查询的结果集
		String sql = "update sc set score=10 where `s#`=8";
		Statement sta = null;
		try {
			sta = conn.createStatement();
			//通过next遍历每一行数据
			sta.executeUpdate(sql);
			sta.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
	}
}
