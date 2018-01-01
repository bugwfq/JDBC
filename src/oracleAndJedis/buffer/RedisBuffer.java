package oracleAndJedis.buffer;

import redis.clients.jedis.Jedis;

/**
 * Redis������
 */
public class RedisBuffer {
	static Jedis jedis = null;
	static OracleDispose oracle;
	static{
		jedis = new Jedis();
		oracle = new OracleDispose();
	}
	//��emp�����Ϣ���浽redis��
	public static void add(Emp emp){
		//���л�emp����
		byte[] a= SerializeDispose.serialize(emp);
		//���뵽redis���ݿ���
		jedis.set(emp.getEmpno().getBytes(),a);
	}
	public static Emp get(String key){
		Emp emp = null;
		//�жϲ�ѯ��
		if(jedis.keys(key).size()>0){
			//��redis��ȡ����Ӧ����Ϣ
			byte[] value = jedis.get(key.getBytes());
			//���������г���
			emp = (Emp)SerializeDispose.unserialize(value);
			return emp;
		}
		
		emp = oracle.oracleInput(key);
		add(emp);
		return emp;
	}
}
