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
		//��ȡ���ӵĶ���
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
		
		//����һ��SimpDateFormat����������ȡ��ʱ��ת��ΪString���͵�ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		//�༭��Ҫִ�е�sql������λ��ʹ�ã����滻
		String sql = "select * from emp where  ename like ? ";
		//����ִ��Ԥ����sql����PreparedStatement
		PreparedStatement psta;
		try {
			psta = conn.prepareStatement(sql);
			//���õ�һ��������ֵ
			psta.setString(1, "%"+inEname+"%");
			//���ز�ѯ�Ľ����
			ResultSet res =  psta.executeQuery();
			while(res.next()){
				//��ȡ���е�Ա�����
				int empno = res.getInt(1);
				//��ȡ���е�Ա������
				String ename = res.getString(2);
				//��ȡ���е�Ա��ְλ
				String job = res.getString(3);
				//��ȡ��ְԱ������
				Timestamp time = res.getTimestamp(5);
				//��ȡ���е�Ա��нˮ
				double sal = res.getDouble(6);
				System.out.println("Ա����ţ�"+empno+" Ա��������"+ename+" ְλ��"+job+" ��ְ���ڣ�"+sdf.format(time)+" ��н��"+sal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ��������������ض�Ӧ����Ϣ
	 * 
	 * @param inEname ģ����ѯ������
	 * @param inJob  ��ѯ��ְλ
	 * @param inSal  ��ѯ��нˮ���ڸ�ֵ
	 */
	public void queryEmp(String inEname,String inJob,String inSal){
		/*
		 *  �������ݿⲢ��ȡ���Ӷ���
	     * ʹ��PreparedStatement����ִ��Ԥ����sql���
		 */
		//��ȡ���ӵĶ���
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
		//����һ��SimpDateFormat����������ȡ��ʱ��ת��ΪString���͵�ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		//��list�������ÿ��λ�ô����ֵ
		List<String> list = new ArrayList<String>();
		//�༭��Ҫִ�е�sql������λ��ʹ�ã����滻
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
		//����ִ��Ԥ����sql����PreparedStatement
		PreparedStatement psta;
		try {
			psta = conn.prepareStatement(sql.toString());
			int index = 1;
			//����list���ϻ�ȡ�����ֵ
			for(Iterator<String> iter = list.iterator();iter.hasNext();){
				String x = iter.next();
				//���������ֵ��˳����ӵ�ÿ����Ӧ��λ��
				psta.setString(index++,x);
			}
			//���ز�ѯ�Ľ����
			ResultSet res =  psta.executeQuery();
			while(res.next()){
				//��ȡ���е�Ա�����
				int empno = res.getInt(1);
				//��ȡ���е�Ա������
				String ename = res.getString(2);
				//��ȡ���е�Ա��ְλ
				String job = res.getString(3);
				//��ȡ��ְԱ������
				Timestamp time = res.getTimestamp(5);
				//��ȡ���е�Ա��нˮ
				double sal = res.getDouble(6);
				System.out.println("Ա����ţ�"+empno+" Ա��������"+ename+" ְλ��"+job+" ��ְ���ڣ�"+sdf.format(time)+" ��н��"+sal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	   /*
	   2.ʹ��plsq��� 1-1000�����е����� ��ֻ�ܱ�1���Լ�����������*/

	/*--------------------------------------------------------------------------------------------------------------------------------*/

	/*��Ŀһ  ��plsql�д�����cc �����������£��� c1,c2����
	c1 c2
	1 ��
	1 ��
	1 ��
	2 ��
	2 ��
	3��
	ת��Ϊ
	1������
	2�������
	3�����

	��Ŀ�� 
	  ���ҳ�������û��£�ÿ�ű�ļ�¼��������ʾ��ʹ�� tab��
	��scott�û�Ϊ����
	���Ӧ���£�?
	  DEPT...................................4?
	  EMP...................................14?
	  BONUS.................................0?
	  SALGRADE.............................5
	  
	  
	��Ŀ ��
	������c�̴���һ���ļ� a.txt
	��������
	  100|���ڲ�|����
	  110|����|��ݸ
	  
	ʹ��plsql��ȡ���ļ� ���� ÿ�е� ���ݲ��뵽dept���� ����ȡһ�� ��|�и
	��ȡ�ļ��Ĵ���
	declare InHandle utl_file.file_type ; --�����ļ�����
	vNewLine VARCHAR2(250);  --���������ȡ��ǰ������
	BEGIN
	vInHandle := utl_file.fopen ('Ŀ¼', '�ļ��� ', 'R');
	utl_file.get_line (vInHandle, vNewLine); --����һ�� ��ȡһ��
	*/



	   
	/*--------------------------------------------------------------------------------------------------------------------------------*/

	/*��ҵ�������ҵ�Ѷ��е�� ������Ҫ2��ʱ�䣩��
	   1 ʹ���α���� scott�����еĹ�Ա���ƣ��������ƣ���н*/

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
		   
			   
	/*	3 д��һ����ҳ�Ĵ洢����
	        ��������
	           tablePager(tableName,curPage,pageSize)
	        ���� 
	           	tablePager('Emp',2,10) 
	            ��ѯemp���� �ڶ�ҳ�����ݣ�ÿҳ��ʾ10�� �ڶ�ҳ���� 10-20����	*/

	/*		
		4 ����һ���洢���� �������
	        ɾ���ñ��е��ظ���¼
	          ���� deleteMul(tableName)
	              ���� deleteMul('emp'); ����ɾ����emp���ظ�����  ��execute immediate    using ��
	    			  
			  */
	


}
