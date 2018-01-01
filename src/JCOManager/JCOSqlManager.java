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
		//���÷���ִ�д洢���̵ķ���
		//callProducesPra_numadd(10,20);
		//����ִ�к����ķ���
		//callFunction_myabs("-12121213");
		//�鿴scott�û��µı�
		//querScottEmp();
		//���ݴ�����������ز�ѯ������
		//querEmpToEnameAndJobAndSal("S",null,null);
		//��ҳ
		//tablePager("EMP",2,3);
		//ɾ�������ظ���¼
		//deleteMul("CC");
		//�����������
		//batchInDest();
		//����Ĳ���
		//transaction();
		//����ʹ��CallProduces����sql���
		//callProducesSql();
	 }
	/**
	 * ʹ��jdbc���ô洢����
	 */
	public void callProducesPra_numadd(int numA ,int numB){
		//��ȡԤ����sql���    ��ʽ   {��=call ������(?)}
		String sql="{call pra_numadd(?,?,?)}";
		//��ȡ����
		Connection conn = null;	
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ��ִ�д洢���̻����Ķ���CallableStatement ��PrepareStatement�������
			CallableStatement call = conn.prepareCall(sql);
			//�����һ��ֵ
			call.setInt(1,numA);
			//����ڶ���ֵ
			call.setInt(2,numB);
			//����ڶ���out�ı���
			call.registerOutParameter(3, Types.INTEGER);
			//ִ��sql���
			call.execute();
			//��ȡ���ص�ֵ
			System.out.println(call.getInt(3));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ���ú���myabs
	 * @param str
	 */
	public void callFunction_myabs(String str){
		//Ԥ�����sql���    ��ʽ   {��=call ������(?)}
		String sql = "{?=call myabs(?)}";
		//��ȡ���ݿ������
		Connection conn = null;
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ�����Ե��ô洢���̺ͺ�����CallableStatement����
			CallableStatement call = conn.prepareCall(sql);
			//ע�᷵�ص�ֵ
			call.registerOutParameter(1, Types.INTEGER);
			//����ʵ��
			call.setString(2, str);
			//ִ��sql���
			call.execute();
			System.out.println(call.getInt(1));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}


	/*��ҵ�������ҵ�Ѷ��е�� ������Ҫ2��ʱ�䣩��
	   1 ʹ���α���� scott�����еĹ�Ա���ƣ��������ƣ���н*/
	public void querScottEmp(){
		//����ƴ��sql����StringBuffer�ַ���
		String sql = "select ename,dname,sal*12 sal from emp e inner join dept d on d.deptno = e.deptno ";
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ��ִ��sql����PreparedStatement�Ķ���
			PreparedStatement ps = conn.prepareStatement(sql);
			//��ȡ�������jdbc�е��α꣩
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String ename = rs.getString("ename");
				String dname = rs.getString("ename");
				double sal = rs.getDouble("sal");
				System.out.println("Ա��������"+ename+"�������ƣ�"+dname+"��н��"+sal);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * ���ݴ�����������ز�ѯ������
	 * �����������Ҫ���Դ���null
	 * @param ename  ģ�����ҵ�����
	 * @param job    ְλ
	 * @param sal    нˮ���ڶ��ٵ�ֵ
	 */
	public void querEmpToEnameAndJobAndSal(String ename,String job,String sal){
		 /*
		   2 ����洢���� ���Դ������²���
		          query(ename,job,sal)
		        ���������ĳ�������� �Բ�����ϵ���ʽ��ѯ���
			    ������ù�������
				   query('Cleck',null,null);
				   ��ѯ��sqlΪ
				   select * from emp where ename like '%Cleck%';
				    query('Cleck','Manager',null);
				   ��ѯ��sqlΪ
				   select * from emp where ename like '%Cleck%' and job like '%Manager%'
				   Ҫ�������ѯ�Ľ��*/
			   
		//�����洢�滻Ԥ����sql����У���ֵ
		List<String> list = new ArrayList<String>();
		//����ƴ��sql����StringBuffer�ַ���
		StringBuilder sql = new StringBuilder("select * from emp where 1=1 ");
		//������enameʱƴ��
		if(ename != null || "".equals(ename)){
			sql.append(" and ename like ? ");
			list.add("%"+ename+"%");
		}
		//������jobʱƴ��
		if(job != null || "".equals(job)){
			sql.append(" and job = ? ");
			list.add("job");
		}
		//������salʱƴ��
		if(sal != null || "".equals(sal)){
			sql.append(" and sal > ��");
			list.add(sal);
		}
		//��ȡ���ӵ����ݿ�
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ��ִ��sql����Statement
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			//��list�����е�
			for(int i = 0 ; i < list.size() ; i++ ){
				ps.setString((i+1), list.get(i));
			}
			//��ȡһ����ȡ����Ϣ�� ResultSetMetaDate
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
	  
			   
	/*	3 д��һ����ҳ�Ĵ洢����
	        ��������
	           tablePager(tableName,curPage,pageSize)
	        ���� 
	           	tablePager('Emp',2,10) 
	            ��ѯemp���� �ڶ�ҳ�����ݣ�ÿҳ��ʾ10�� �ڶ�ҳ���� 10-20����	*/
	/**
	 * һ����ָ�����ҳ�ķ���
	 * ����һ������   �ڼ�ҳ  ÿҳ��ʾ������
	 * @param tableName   ����
	 * @param curPage   ҳ��
	 * @param pageSize  ����
	 */
	public void tablePager(String tableName,int curPage, int pageSize){
		//���е�sql���
		String sql= "Select * from (select t.*,rownum rn from "+tableName+" t) tt where tt.rn between ? and ?";
		//��ȡ���ӵ����ݿ�
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ��ִ��sql����Statement
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			//����Ӧ��ֵ�滻
			ps.setInt(1, ((curPage-1)*pageSize+1));
			ps.setInt(2, (curPage*pageSize));
			//��ȡһ����ȡ����Ϣ�� ResultSetMetaDate
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
		4 ����һ���洢���� �������
	        ɾ���ñ��е��ظ���¼
	          ���� deleteMul(tableName)
	              ���� deleteMul('emp'); ����ɾ����emp���ظ�����  ��execute immediate    using ��
	    			  
			  */
	/**
	 * ͨ������ɾ�����е��ظ�����
	 * @param tableName
	 */
	public void deleteMul(String tableName){
		//���е�sql���
		
		//��ȡ���ӵ����ݿ�
		Connection conn;
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ��ִ��sql����Statement
			Statement ps = conn.createStatement();
			//��ȡһ��ResultSetMetaData ����һ����ѯ����ѯ����ı������Ϣ
			ResultSetMetaData rsmd = ps.executeQuery("select * from "+tableName).getMetaData();
			//����һ��StringBuilder�����洢�е���Ϣ
			StringBuilder sb = new StringBuilder("");
			//��ȡ��������
			for(int i = 1 ;i<=rsmd.getColumnCount();i++){
				//����ȡ������׷�ӵ��ַ�����
				sb.append(rsmd.getColumnName(i)+",");
			}
			//�����һ��','��ȥ��
			String columns = sb.substring(0, (sb.length()-1));
			//����ִ�����
			String sql= "delete from "+tableName+" where rowid not in (select min(rowid) from "+tableName+" group by "+columns+")";
			//ִ��sql��䲢����Ӱ�������
			System.out.println(ps.executeUpdate(sql));;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������ֵ
	 */
	public void batchInDest(){
		String sql = "insert into CC values(?,?)";
		//��ȡһ������
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
			//��������һЩֵ
			for(int i = 0;i<100;i++){
				
					ps.setInt(1, 3);
					ps.setString(2, "��");
					ps.addBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			//ִ���������sql���
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �������ϰ
	 */
	public void transaction(){
		//��ȡһ������
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
		//��Ҫִ�е�sql���
		String sql="update CC set C1=2 where c1=3";
		//��ȡһ������ִ��Ԥ����sql����PreparedStatement����
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
		//��Ҫִ�е�sql���
		String sql2="delete from CC where c1=2";
		//��ȡһ������ִ��Ԥ����sql����PreparedStatement����
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
		//��ȡԤ����sql���    ��ʽ   {��=call ������(?)}
		String sql="{select * from emp}";
		//��ȡ����
		Connection conn = null;	
		try {
			conn = ConnectionTool.getConnection();
			//��ȡһ��ִ�д洢���̻����Ķ���CallableStatement ��PrepareStatement�������
			CallableStatement ps = conn.prepareCall(sql);

			//ִ��sql���
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
