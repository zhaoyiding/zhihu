package zyd.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyd.zhihu.dao.MessageDao;
import zyd.zhihu.model.Message;
import zyd.zhihu.utils.SensitiveWordTool;

import java.util.List;

@Service
public class MessageService {
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private SensitiveWordTool tool;
	
	public int addMessage(Message message) {
		message.setContent(tool.checkText(message.getContent()));
		return messageDao.addMessage(message);
	}
	
	public List<Message> getConversationList(int userId, int offset, int limit) {
		return messageDao.getConversationList(userId, offset, limit);
	}
	
	public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
		return messageDao.getConversationDetail(conversationId, offset, limit);
	}
	
	public int getConversationUnreadCount(String conversationId) {
		return messageDao.getConversationUnreadCount(conversationId);
	}
	
	public int getMessageCount(String conversationId) {
		return messageDao.getMessageCount(conversationId);
	}
	
	public int setMessageHasRead(int id, int hasRead) {
		return messageDao.setMessageHasRead(id, hasRead);
	}
}
