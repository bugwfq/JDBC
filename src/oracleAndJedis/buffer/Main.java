package oracleAndJedis.buffer;

import java.util.Scanner;
/**
 * 4、使用java实现一个结合jdbc和redis实现的缓存系统 需求如下（24）
   数据库表 emp表（雇员编号，雇员名称，职位，薪水） 不限数据库
   Java中提供 查询方法(通过雇员编号查询获取到对应的雇员信息 返回
Emp对象（自建实体类 字段和数据库相同）)
Public Emp query(String empNo){
   //如果缓存中存在 直接从缓存中获取返回
   //如果缓存中不存在 使用jdbc查询数据库并放入缓存 并返回
}
 * @author Administrator
 *
 */

public class Main {
	//scanner流获取用户输入的内容
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args){
		//调用菜单
		menu();
	}
	public static void menu(){
		System.out.println("请输入要查找的员工编号");
		//获取要查询的员工编号
		String empno=input.next();
		Emp emp = RedisBuffer.get(empno);
		if(emp == null){
			System.out.println("没有该员工");
		}else{
			System.out.println(emp);
		}
		
	}
}
