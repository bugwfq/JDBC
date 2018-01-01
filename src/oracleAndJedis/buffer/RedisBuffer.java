package oracleAndJedis.buffer;

import redis.clients.jedis.Jedis;

/**
 * Redis缓存区
 */
public class RedisBuffer {
	static Jedis jedis = null;
	static OracleDispose oracle;
	static{
		jedis = new Jedis();
		oracle = new OracleDispose();
	}
	//将emp表的信息缓存到redis中
	public static void add(Emp emp){
		//序列化emp对象
		byte[] a= SerializeDispose.serialize(emp);
		//加入到redis数据库中
		jedis.set(emp.getEmpno().getBytes(),a);
	}
	public static Emp get(String key){
		Emp emp = null;
		//判断查询的
		if(jedis.keys(key).size()>0){
			//从redis中取出对应的信息
			byte[] value = jedis.get(key.getBytes());
			//将对象反序列出来
			emp = (Emp)SerializeDispose.unserialize(value);
			return emp;
		}
		
		emp = oracle.oracleInput(key);
		add(emp);
		return emp;
	}
}
