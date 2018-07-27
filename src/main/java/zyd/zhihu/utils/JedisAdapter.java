package zyd.zhihu.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class JedisAdapter {
	private static final Logger LOGGER = Logger.getLogger("JedisAdapter");
	
	private JedisPool pool = new JedisPool();
	
	public Jedis getJedis() {
		return pool.getResource();
	}
	
	public long sadd(String key, String... value) {
		try (Jedis jedis = getJedis()) {
			return jedis.sadd(key, value);
		} catch (Exception e) {
			LOGGER.severe("sadd 失败：" + e.getMessage());
			return 0;
		}
	}
	
	public long srem(String key, String... value) {
		try (Jedis jedis = getJedis()) {
			return jedis.srem(key, value);
		} catch (Exception e) {
			LOGGER.severe("srem 失败：" + e.getMessage());
			return 0;
		}
	}
	
	public long scard(String key) {
		try (Jedis jedis = getJedis()) {
			return jedis.scard(key);
		} catch (Exception e) {
			LOGGER.severe("scard 失败：" + e.getMessage());
			return 0;
		}
	}
	
	public boolean sismember(String key, String value) {
		try (Jedis jedis = getJedis()) {
			return jedis.sismember(key, value);
		} catch (Exception e) {
			LOGGER.severe("sismember 失败：" + e.getMessage());
			return false;
		}
	}
	
	public long rpush(String key, String... value) {
		try (Jedis jedis = getJedis()) {
			return jedis.rpush(key, value);
		} catch (Exception e) {
			LOGGER.severe("rpush 失败：" + e.getMessage());
			return 0;
		}
	}
	
	public List<String> lrange(String key, long start, long end) {
		try (Jedis jedis = getJedis()) {
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			LOGGER.severe("lrange 失败：" + e.getMessage());
			return null;
		}
	}
	
	public List<String> blpop(int timeout, String... key) {
		try (Jedis jedis = getJedis()) {
			return jedis.blpop(timeout, key);
		} catch (Exception e) {
			LOGGER.severe("blpop 失败：" + e.getMessage());
			return null;
		}
	}
	
	public Transaction getTransaction(Jedis jedis) {
		try {
			return jedis.multi();
		} catch (Exception e) {
			LOGGER.severe("multi 失败：" + e.getMessage());
			return null;
		}
	}
	
	public List<Object> exec(Transaction tx, Jedis jedis) {
		try {
			return tx.exec();
		} catch (Exception e) {
			LOGGER.severe("exec 失败：" + e.getMessage());
			tx.discard();
		} finally {
			if (tx != null) {
				try {
					tx.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (jedis != null) {
				jedis.close();
			}
			
		}
		return null;
	}
	
	public List<Integer> zrevrange(String key, int start, int end) {
		try (Jedis jedis = getJedis()) {
			return strSetToIntegerList(jedis.zrevrange(key, start, end));
		} catch (Exception e) {
			LOGGER.severe("zrevrange 失败：" + e.getMessage());
			return null;
		}
	}
	
	private List<Integer> strSetToIntegerList(Set<String> set) {
		List<Integer> list = new ArrayList<>();
		if (set == null) {
			return list;
		}
		for (String s : set) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}
	
	public long zcard(String key) {
		try (Jedis jedis = getJedis()) {
			return jedis.zcard(key);
		} catch (Exception e) {
			LOGGER.severe("zcard 失败：" + e.getMessage());
			return 0;
		}
	}
	
	public Double zscore(String key, String member) {
		try (Jedis jedis = getJedis()) {
			return jedis.zscore(key, member);
		} catch (Exception e) {
			LOGGER.severe("zscore 失败：" + e.getMessage());
			return null;
		}
	}
}
