package zyd.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyd.zhihu.utils.JedisAdapter;

import static zyd.zhihu.utils.RedisKeyUtil.getDisLikeKey;
import static zyd.zhihu.utils.RedisKeyUtil.getLikeKey;

@Service
public class LikeService {
	
	@Autowired
	private JedisAdapter adapter;
	
	public long like(int entityType, int entityId, int userId) {
		String dislikeKey = getDisLikeKey(entityType, entityId);
		adapter.srem(dislikeKey, String.valueOf(userId));
		
		String likeKey = getLikeKey(entityType, entityId);
		adapter.sadd(likeKey, String.valueOf(userId));
		
		//返回喜欢的人数
		return adapter.scard(likeKey);
	}
	
	public long dislike(int entityType, int entityId, int userId) {
		String likeKey = getLikeKey(entityType, entityId);
		adapter.srem(likeKey, String.valueOf(userId));
		
		String dislikeKey = getDisLikeKey(entityType, entityId);
		adapter.sadd(dislikeKey, String.valueOf(userId));
		
		//返回喜欢的人数
		return adapter.scard(likeKey);
	}
	
	public long getLikeCount(int entityType, int entityId) {
		String likeKey = getLikeKey(entityType, entityId);
		
		//返回喜欢的人数
		return adapter.scard(likeKey);
	}
	
	
	/*
	* 喜欢返回1
	* 不喜欢返回-1
	* 未投票返回0
	* */
	public int getLikeStatus(int entityType, int entityId, int userId) {
		String likeKey = getLikeKey(entityType, entityId);
		if (adapter.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}
		
		String dislikeKey = getDisLikeKey(entityType, entityId);
		return adapter.sismember(dislikeKey, String.valueOf(userId)) ? -1 : 0;
	}
}
