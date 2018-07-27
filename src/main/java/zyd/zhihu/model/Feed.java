package zyd.zhihu.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feed {
	private Integer id;
	
	private Date createdTime;
	
	private Integer userId;
	
	private Integer type;
	
	private Map<String, String> data;
	
	public Feed(Integer id, Date createdTime, Integer userId, Integer type) {
		this.id = id;
		this.createdTime = createdTime;
		this.userId = userId;
		this.type = type;
	}
	
	public Feed() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getData() {
		return JSONObject.toJSONString(data);
	}
	
	public void setData(String data) {
		this.data = JSONObject.parseObject(data, Map.class);
	}
	
	public String get(String key) {
		return data.get(key);
	}
	
	public void set(String key, String value) {
		if (data == null) {
			data = new HashMap<>();
		}
		data.put(key, value);
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
}