package zyd.zhihu.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
	private EventType type;
	private int actorId;
	private int entityType;
	private int entityId;
	private int entityOwnerId;
	
	private Map<String, String> exts = new HashMap<>();
	
	/*
	* JSON序列化和反序列化时，需要用到默认构造器和各个域的get和set方法，
	* 如果缺少这些会造成反序列化错误
	* 对象序列化使用JSONObject.parse
	* */
	public EventModel() {
	}
	
	public EventModel(EventType type, int actorId, int entityType, int entityId, int entityOwnerId) {
		this.type = type;
		this.actorId = actorId;
		this.entityType = entityType;
		this.entityId = entityId;
		this.entityOwnerId = entityOwnerId;
	}
	
	public String get(String key) {
		return exts.get(key);
	}
	
	public void put(String key, String val) {
		exts.put(key, val);
	}
	
	public EventType getType() {
		return type;
	}
	
	public void setType(EventType type) {
		this.type = type;
	}
	
	public int getActorId() {
		return actorId;
	}
	
	public void setActorId(int actorId) {
		this.actorId = actorId;
	}
	
	public int getEntityType() {
		return entityType;
	}
	
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}
	
	public int getEntityId() {
		return entityId;
	}
	
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	public int getEntityOwnerId() {
		return entityOwnerId;
	}
	
	public void setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
	}
	
	public Map<String, String> getExts() {
		return exts;
	}
	
	public void setExts(Map<String, String> exts) {
		this.exts = exts;
	}
}
