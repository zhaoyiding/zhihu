package zyd.zhihu.utils;

public class RedisKeyUtil {
	private static final String APP = "ZHIHU";
	private static final String SPLIT = ":";
	private static final String LIKE = "LIKE";
	private static final String DISLIKE = "DISLIKE";
	private static final String EVENT_QUEUE = "EVENT_QUEUE";
	
	//粉丝
	private static final String FOLLOWER = "FOLLOWER";
	//关注对象
	private static final String FOLLOWEE = "FOLLOWEE";
	//时间线
	private static final String TIMELINE = "TIMELINE";
	
	public static String getLikeKey(int entityType, int entityId) {
		StringBuilder sb = new StringBuilder();
		sb.append(APP).append(SPLIT).append(LIKE).append(SPLIT).append(entityType).append(SPLIT).append(entityId);
		return sb.toString();
	}
	
	public static String getDisLikeKey(int entityType, int entityId) {
		StringBuilder sb = new StringBuilder();
		sb.append(APP).append(SPLIT).append(DISLIKE).append(SPLIT).append(entityType).append(SPLIT).append(entityId);
		return sb.toString();
	}
	
	public static String getEventQueueKey() {
		return APP + SPLIT + EVENT_QUEUE;
	}
	
	public static String getFollowerKey(int entityType, int entityId) {
		StringBuilder sb = new StringBuilder();
		sb.append(APP).append(SPLIT).append(FOLLOWER).append(SPLIT).append(entityType).append(SPLIT).append(entityId);
		return sb.toString();
	}
	
	public static String getFolloweeKey(int userId, int entityType) {
		StringBuilder sb = new StringBuilder();
		sb.append(APP).append(SPLIT).append(FOLLOWEE).append(SPLIT).append(userId).append(SPLIT).append(entityType);
		return sb.toString();
	}
	
	public static String getTimeline(int userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(APP).append(SPLIT).append(TIMELINE).append(SPLIT).append(userId);
		return sb.toString();
	}
}
