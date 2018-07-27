package zyd.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import zyd.zhihu.utils.JedisAdapter;

import java.util.Date;
import java.util.List;

import static zyd.zhihu.utils.RedisKeyUtil.getFolloweeKey;
import static zyd.zhihu.utils.RedisKeyUtil.getFollowerKey;

@Service
public class FollowService {
	@Autowired
	private JedisAdapter adapter;
	
	public boolean follow(int userId, int entityType, int entityId) {
		String followerKey = getFollowerKey(entityType, entityId);
		String followeeKey = getFolloweeKey(userId, entityType);
		
		Date date = new Date();
		
		Jedis jedis = adapter.getJedis();
		Transaction tx = adapter.getTransaction(jedis);
		tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
		tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
		List<Object> res = adapter.exec(tx, jedis);
		
		if (res != null
				&& res.size() == 2
				&& (Long) res.get(0) > 0
				&& (Long) res.get(1) > 0) {
			return true;
		}
		return false;
	}
	
	public boolean unfollow(int userId, int entityType, int entityId) {
		String followerKey = getFollowerKey(entityType, entityId);
		String followeeKey = getFolloweeKey(userId, entityType);
		
		Jedis jedis = adapter.getJedis();
		Transaction tx = adapter.getTransaction(jedis);
		tx.zrem(followerKey, String.valueOf(userId));
		tx.zrem(followeeKey, String.valueOf(entityId));
		List<Object> res = adapter.exec(tx, jedis);
		
		if (res != null
				&& res.size() == 2
				&& (Long) res.get(0) > 0
				&& (Long) res.get(1) > 0) {
			return true;
		}
		return false;
	}
	
	public List<Integer> getFollowerId(int entityType, int entityId, int start, int end) {
		String key = getFollowerKey(entityType, entityId);
		return adapter.zrevrange(key, start, end);
	}
	
	public List<Integer> getFolloweeId(int userId, int entityType, int start, int end) {
		String key = getFolloweeKey(userId, entityType);
		return adapter.zrevrange(key, start, end);
	}
	
	public long getFollowerCount(int entityType, int entityId) {
		String key = getFollowerKey(entityType, entityId);
		return adapter.zcard(key);
	}
	
	public long getFolloweeCount(int userId, int entityType) {
		String key = getFolloweeKey(userId, entityType);
		return adapter.zcard(key);
	}
	
	public boolean isFollower(int userId, int entityType, int entityId) {
		String key = getFollowerKey(entityType, entityId);
		return adapter.zscore(key, String.valueOf(userId)) != null;
	}
}
