package oracleAndJedis.buffer;

import java.util.Scanner;
/**
 * 4��ʹ��javaʵ��һ�����jdbc��redisʵ�ֵĻ���ϵͳ �������£�24��
   ���ݿ�� emp����Ա��ţ���Ա���ƣ�ְλ��нˮ�� �������ݿ�
   Java���ṩ ��ѯ����(ͨ����Ա��Ų�ѯ��ȡ����Ӧ�Ĺ�Ա��Ϣ ����
Emp�����Խ�ʵ���� �ֶκ����ݿ���ͬ��)
Public Emp query(String empNo){
   //��������д��� ֱ�Ӵӻ����л�ȡ����
   //��������в����� ʹ��jdbc��ѯ���ݿⲢ���뻺�� ������
}
 * @author Administrator
 *
 */

public class Main {
	//scanner����ȡ�û����������
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args){
		//���ò˵�
		menu();
	}
	public static void menu(){
		System.out.println("������Ҫ���ҵ�Ա�����");
		//��ȡҪ��ѯ��Ա�����
		String empno=input.next();
		Emp emp = RedisBuffer.get(empno);
		if(emp == null){
			System.out.println("û�и�Ա��");
		}else{
			System.out.println(emp);
		}
		
	}
}
