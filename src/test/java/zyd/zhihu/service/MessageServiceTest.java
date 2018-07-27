package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.Message;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class MessageServiceTest {
	@Autowired
	private MessageService ms;
	
	@Test
	public void setMessageHasRead() throws Exception {
		System.out.println(ms.setMessageHasRead(10, 1));
	}
	
	@Test
	public void getMessageCount() throws Exception {
		System.out.println(ms.getMessageCount("8_10"));
	}
	
	@Test
	public void addMessage() throws Exception {
		ms.addMessage(new Message(0, 10, 8, new Date(), 0, "10向8问好"));
		ms.addMessage(new Message(0, 8, 10, new Date(), 0, "8向10问好"));
		ms.addMessage(new Message(0, 10, 1, new Date(), 0, "10向1问好"));
	}
	
	@Test
	public void getConversationList() throws Exception {
		List<Message> messages = ms.getConversationList(10, 0, 100);
		System.out.println(messages.size());
	}
	
	@Test
	public void getConversationDetail() throws Exception {
		List<Message> messages = ms.getConversationDetail("8_10", 0, 100);
		System.out.println(messages.size());
	}
	
	@Test
	public void getConversationUnreadCount() throws Exception {
		System.out.println(ms.getConversationUnreadCount("8_10"));
	}
	
}