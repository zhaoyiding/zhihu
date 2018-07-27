package zyd.zhihu.model;

import zyd.zhihu.utils.MyUtil;

import java.util.Date;

public class Message {
	private Integer id;
	
	private Integer fromId;
	
	private Integer toId;
	
	private String conversationId;
	
	private Date createdTime;
	
	private Integer hasRead;
	
	private String content;
	
	public Message(Integer id, Integer fromId, Integer toId, Date createdTime, Integer hasRead, String content) {
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.conversationId = MyUtil.getConversationId(fromId, toId);
		this.createdTime = createdTime;
		this.hasRead = hasRead;
		this.content = content;
	}
	
	public Message() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFromId() {
		return fromId;
	}
	
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	
	public Integer getToId() {
		return toId;
	}
	
	public void setToId(Integer toId) {
		this.toId = toId;
	}
	
	public String getConversationId() {
		return conversationId;
	}
	
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Integer getHasRead() {
		return hasRead;
	}
	
	public void setHasRead(Integer hasRead) {
		this.hasRead = hasRead;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}