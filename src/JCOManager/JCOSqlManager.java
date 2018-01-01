package JCOManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import JCOconnection.ConnectionTool;

public class JCOSqlManager {
	@Test
	 public void main(){
		//调用方法执行存储过程的方法
		//callProducesPra_numadd(10,20);
		//调用执行函数的方法
		//callFunction_myabs("-12121213");
		//查看scott用户下的表
		//querScottEmp();
		//根据传入的条件返回查询的数据
		//querEmpToEnameAndJobAndSal("S",null,null);
		//分页
		//tablePager("EMP",2,3);
		//删除表中重复记录
		//deleteMul("CC");
		//批量添加数据
		//batchInDest();
		//事务的操作
		//transaction();
		//不能使用CallProduces调用sql语句
		//callProducesSql();
	 }
	/**
	 * 使用jdbc调用存储过程
	 */
	public void callProducesPra_numadd(int numA ,int numB){
		//获取预编译sql语句    格式   {？=call 函数名(?)}
		String sql="{call pra_numadd(?,?,?)}";
		//获取连接
		Connection conn = null;	
		try {
			conn = ConnectionTool.getConnection();
			//获取一个执行存储过程或函数的对象CallableStatement 是PrepareStatement类的子类
			CallableStatement call = conn.prepareCall(sql);
			//传入第一个值
			call.setInt(1,numA);
			//传入第二个值
			call.setInt(2,numB);
			//出册第二个out的变量
			call.registerOutParameter(3, Types.INTEGER);
			//执行sql语句
			call.execute();
			//获取返回的值
			System.out.println(call.getInt(3));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 调用函数myabs
	 * @param str
	 */
	public void callFunction_myabs(String str){
		//预编译的sql语句    格式   {？=call 函数名(?)}
		String sql = "{?=call myabs(?)}";
		//获取数据库的连接
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			//获取一个可以调用存储过程和函数的CallableStatement对象
			CallableStatement call = conn.prepareCall(sql);
			//注册返回的值
			call.registerOutParameter(1, Types.INTEGER);
			//传入实参
			call.setString(2, str);
			//执行sql语句
			call.execute();
			System.out.println(call.getInt(1));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}


	/*作业（这个作业难度有点大 可能需要2天时间）：
	   1 使用游标输出 scott中所有的雇员名称，部门名称，年薪*/
	public void querScottEmp(){
		//用来拼接sql语句的StringBuffer字符串
		String sql = "select ename,dname,sal*12 sal from emp e inner join dept d on d.deptno = e.deptno ";
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//获取一个执行sql语句的PreparedStatement的对象
			PreparedStatement ps = conn.prepareStatement(sql);
			//获取结果集（jdbc中的游标）
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String ename = rs.getString("ename");
				String dname = rs.getString("ename");
				double sal = rs.getDouble("sal");
				System.out.println("员工姓名："+ename+"部门名称："+dname+"年薪："+sal);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * 根据传入的条件返回查询的数据
	 * 如果条件不需要可以传入null
	 * @param ename  模糊查找的姓名
	 * @param job    职位
	 * @param sal    薪水大于多少的值
	 */
	public void querEmpToEnameAndJobAndSal(String ename,String job,String sal){
		 /*
		   2 定义存储过程 可以传入以下参数
		          query(ename,job,sal)
		        如果传入了某几个参数 以参数组合的形式查询结果
			    比如调用过程如下
				   query('Cleck',null,null);
				   查询的sql为
				   select * from emp where ename like '%Cleck%';
				    query('Cleck','Manager',null);
				   查询的sql为
				   select * from emp where ename like '%Cleck%' and job like '%Manager%'
				   要求输出查询的结果*/
			   
		//用来存储替换预编译sql语句中？的值
		List<String> list = new ArrayList<String>();
		//用来拼接sql语句的StringBuffer字符串
		StringBuilder sql = new StringBuilder("select * from emp where 1=1 ");
		//当传入ename时拼接
		if(ename != null || "".equals(ename)){
			sql.append(" and ename like ? ");
			list.add("%"+ename+"%");
		}
		//当传入job时拼接
		if(job != null || "".equals(job)){
			sql.append(" and job = ? ");
			list.add("job");
		}
		//当传入sal时拼接
		if(sal != null || "".equals(sal)){
			sql.append(" and sal > ？");
			list.add(sal);
		}
		//获取连接的数据库
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//获取一个执行sql语句的Statement
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			//将list集合中的
			for(int i = 0 ; i < list.size() ; i++ ){
				ps.setString((i+1), list.get(i));
			}
			//获取一个获取列信息的 ResultSetMetaDate
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData psm =  rs.getMetaData();
			for(int i =1 ; i < psm.getColumnCount(); i ++){
				System.out.print (psm.getColumnName(i)+"\t");
			}
			System.out.println("");
			while(rs.next()){
				StringBuilder sb = new StringBuilder("");
				for(int i =1 ; i < psm.getColumnCount(); i ++){
					String column = rs.getString(i);
					if(column!=null &&column.contains(" 00:00:00")){
						column = column.replaceAll(" 00:00:00", "");
					}
					sb.append(column+"\t");
				}
				System.out.println(sb);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	  
			   
	/*	3 写出一个分页的存储过程
	        定义如下
	           tablePager(tableName,curPage,pageSize)
	        调用 
	           	tablePager('Emp',2,10) 
	            查询emp表中 第二页的数据（每页显示10条 第二页就是 10-20条）	*/
	/**
	 * 一个对指定表分页的方法
	 * 传入一个表名   第几页  每页显示的条数
	 * @param tableName   表名
	 * @param curPage   页数
	 * @param pageSize  条数
	 */
	public void tablePager(String tableName,int curPage, int pageSize){
		//运行的sql语句
		String sql= "Select * from (select t.*,rownum rn from "+tableName+" t) tt where tt.rn between ? and ?";
		//获取连接的数据库
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//获取一个执行sql语句的Statement
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			//将对应的值替换
			ps.setInt(1, ((curPage-1)*pageSize+1));
			ps.setInt(2, (curPage*pageSize));
			//获取一个获取列信息的 ResultSetMetaDate
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData psm =  rs.getMetaData();
			for(int i =1 ; i < psm.getColumnCount(); i ++){
				System.out.print (psm.getColumnName(i)+"\t\t");
			}
			System.out.println("");
			while(rs.next()){
				StringBuilder sb = new StringBuilder("");
				for(int i =1 ; i < psm.getColumnCount(); i ++){
					String column = rs.getString(i);
					if(column!=null &&column.contains(" 00:00:00")){
						column = column.replaceAll(" 00:00:00", "");
					}
					sb.append(column+"\t\t");
				}
				System.out.println(sb);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*		
		4 定义一个存储过程 传入表名
	        删除该表中的重复记录
	          比如 deleteMul(tableName)
	              调用 deleteMul('emp'); 必须删除表emp的重复数据  （execute immediate    using ）
	    			  
			  */
	/**
	 * 通过表名删除表中的重复数据
	 * @param tableName
	 */
	public void deleteMul(String tableName){
		//运行的sql语句
		
		//获取连接的数据库
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//获取一个执行sql语句的Statement
			Statement ps = conn.createStatement();
			//获取一个ResultSetMetaData 传入一个查询语句查询传入的表的列信息
			ResultSetMetaData rsmd = ps.executeQuery("select * from "+tableName).getMetaData();
			//创建一个StringBuilder用来存储列的信息
			StringBuilder sb = new StringBuilder("");
			//获取所有列明
			for(int i = 1 ;i<=rsmd.getColumnCount();i++){
				//将获取的列名追加到字符穿中
				sb.append(rsmd.getColumnName(i)+",");
			}
			//将最后一个','号去掉
			String columns = sb.substring(0, (sb.length()-1));
			//创建执行语句
			String sql= "delete from "+tableName+" where rowid not in (select min(rowid) from "+tableName+" group by "+columns+")";
			//执行sql语句并返回影响的行数
			System.out.println(ps.executeUpdate(sql));;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量插入值
	 */
	public void batchInDest(){
		String sql = "insert into CC values(?,?)";
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
			for(int i = 0;i<100;i++){
				
					ps.setInt(1, 3);
					ps.setString(2, "好");
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
	
	/**
	 * 事物的练习
	 */
	public void transaction(){
		//获取一个连接
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//需要执行的sql语句
		String sql="update CC set C1=2 where c1=3";
		//获取一个可以执行预编译sql语句的PreparedStatement对象
		PreparedStatement ps ;
		try {
			 ps = conn.prepareStatement(sql);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = null;
		System.out.println(s.equals(""));
		//需要执行的sql语句
		String sql2="delete from CC where c1=2";
		//获取一个可以执行预编译sql语句的PreparedStatement对象
		try {
			 ps = conn.prepareStatement(sql2);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void callProducesSql(){
		//获取预编译sql语句    格式   {？=call 函数名(?)}
		String sql="{select * from emp}";
		//获取连接
		Connection conn = null;	
		try {
			conn = ConnectionTool.getConnection();
			//获取一个执行存储过程或函数的对象CallableStatement 是PrepareStatement类的子类
			CallableStatement ps = conn.prepareCall(sql);

			//执行sql语句
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData psm =  rs.getMetaData();
			for(int i =1 ; i < psm.getColumnCount(); i ++){
				System.out.print (psm.getColumnName(i)+"\t\t");
			}
			System.out.println("");
			while(rs.next()){
				StringBuilder sb = new StringBuilder("");
				for(int i =1 ; i < psm.getColumnCount(); i ++){
					String column = rs.getString(i);
					if(column!=null &&column.contains(" 00:00:00")){
						column = column.replaceAll(" 00:00:00", "");
					}
					sb.append(column+"\t\t");
				}
				System.out.println(sb);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
