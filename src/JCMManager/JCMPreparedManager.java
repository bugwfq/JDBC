package JCMManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import JCMconnection.ConnectionTool;

public class JCMPreparedManager {
	@Test
	public void call(){
		//selectCase("' or 1=1 or 1=' ","' or 1=1 or 1=","1");
		//selectSqlError("");
		queryEmp(null,null,"1500");
	}
	public void selectSqlError(String inEname){
		//获取链接的对象
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//创建一个SimpDateFormat类用来将获取的时间转化为String类型的时间
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		//编辑需要执行的sql语句变量位置使用？号替换
		String sql = "select * from emp where  ename like ? ";
		//创建执行预编译sql语句的PreparedStatement
		PreparedStatement psta;
		try {
			psta = conn.prepareStatement(sql);
			//设置第一个变量的值
			psta.setString(1, "%"+inEname+"%");
			//返回查询的结果集
			ResultSet res =  psta.executeQuery();
			while(res.next()){
				//获取该行的员工编号
				int empno = res.getInt(1);
				//获取该行的员工姓名
				String ename = res.getString(2);
				//获取该行的员工职位
				String job = res.getString(3);
				//获取入职员工日期
				Timestamp time = res.getTimestamp(5);
				//获取该行的员工薪水
				double sal = res.getDouble(6);
				System.out.println("员工编号："+empno+" 员工姓名："+ename+" 职位："+job+" 入职日期："+sdf.format(time)+" 月薪："+sal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 传入查找条件返回对应的信息
	 * 
	 * @param inEname 模糊查询的人名
	 * @param inJob  查询的职位
	 * @param inSal  查询的薪水大于该值
	 */
	public void queryEmp(String inEname,String inJob,String inSal){
		/*
		 *  连接数据库并获取连接对象
	     * 使用PreparedStatement对象执行预编译sql语句
		 */
		//获取链接的对象
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//创建一个SimpDateFormat类用来将获取的时间转化为String类型的时间
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		//用list集合添加每个位置传入的值
		List<String> list = new ArrayList<String>();
		//编辑需要执行的sql语句变量位置使用？号替换
		StringBuilder sql = new StringBuilder( "select * from emp where 1=1");
		if(inEname!=null && !inEname.equals("")){
			sql.append(" and ename like ? ");
			list.add("%"+inEname+"%");
		}
		if(inJob!=null && !inJob.equals("")){
			sql.append(" and job=? ");
			list.add(inJob);
		}
		if(inSal!=null && !inSal.equals("")){
			sql.append(" and sal >? ");
			list.add(inSal);
		}
		//创建执行预编译sql语句的PreparedStatement
		PreparedStatement psta;
		try {
			psta = conn.prepareStatement(sql.toString());
			int index = 1;
			//遍历list集合获取传入的值
			for(Iterator<String> iter = list.iterator();iter.hasNext();){
				String x = iter.next();
				//将将加入的值按顺序添加到每个对应的位置
				psta.setString(index++,x);
			}
			//返回查询的结果集
			ResultSet res =  psta.executeQuery();
			while(res.next()){
				//获取该行的员工编号
				int empno = res.getInt(1);
				//获取该行的员工姓名
				String ename = res.getString(2);
				//获取该行的员工职位
				String job = res.getString(3);
				//获取入职员工日期
				Timestamp time = res.getTimestamp(5);
				//获取该行的员工薪水
				double sal = res.getDouble(6);
				System.out.println("员工编号："+empno+" 员工姓名："+ename+" 职位："+job+" 入职日期："+sdf.format(time)+" 月薪："+sal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	   /*
	   2.使用plsq输出 1-1000中所有的质数 （只能被1和自己整除的数）*/

	/*--------------------------------------------------------------------------------------------------------------------------------*/

	/*题目一  在plsql中创建表cc 插入数据如下（列 c1,c2）：
	c1 c2
	1 西
	1 安
	1 的
	2 天
	2 气
	3好
	转换为
	1西安的
	2天气输出
	3好输出

	题目二 
	  查找出输入的用户下，每张表的记录数，（提示：使用 tab表）
	以scott用户为例，
	结果应如下：?
	  DEPT...................................4?
	  EMP...................................14?
	  BONUS.................................0?
	  SALGRADE.............................5
	  
	  
	题目 三
	加载在c盘存在一个文件 a.txt
	内容如下
	  100|金融部|深圳
	  110|财务部|东莞
	  
	使用plsql读取该文件 并将 每行的 数据插入到dept表中 （读取一行 按|切割）
	读取文件的代码
	declare InHandle utl_file.file_type ; --定义文件类型
	vNewLine VARCHAR2(250);  --定义变量获取当前行数据
	BEGIN
	vInHandle := utl_file.fopen ('目录', '文件名 ', 'R');
	utl_file.get_line (vInHandle, vNewLine); --调用一次 读取一行
	*/



	   
	/*--------------------------------------------------------------------------------------------------------------------------------*/

	/*作业（这个作业难度有点大 可能需要2天时间）：
	   1 使用游标输出 scott中所有的雇员名称，部门名称，年薪*/

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
		   
			   
	/*	3 写出一个分页的存储过程
	        定义如下
	           tablePager(tableName,curPage,pageSize)
	        调用 
	           	tablePager('Emp',2,10) 
	            查询emp表中 第二页的数据（每页显示10条 第二页就是 10-20条）	*/

	/*		
		4 定义一个存储过程 传入表名
	        删除该表中的重复记录
	          比如 deleteMul(tableName)
	              调用 deleteMul('emp'); 必须删除表emp的重复数据  （execute immediate    using ）
	    			  
			  */
	


}
