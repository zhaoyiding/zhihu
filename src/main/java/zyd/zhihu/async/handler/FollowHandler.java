package zyd.zhihu.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zyd.zhihu.async.EventHandler;
import zyd.zhihu.async.EventModel;
import zyd.zhihu.async.EventType;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.Message;
import zyd.zhihu.model.User;
import zyd.zhihu.service.MessageService;
import zyd.zhihu.service.UserService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void doHandle(EventModel eventModel) {
		User actor = userService.getUserById(eventModel.getActorId());
		
		StringBuilder content = new StringBuilder();
		if (eventModel.getEntityType() == EntityType.QUESTION) {
			content.append("用户").append(actor.getName()).append("关注了你的问题：")
					.append("http://127.0.0.1:8080/question/").append(eventModel.getEntityId());
		} else if (eventModel.getEntityType() == EntityType.USER) {
			content.append("用户").append(actor.getName()).append("关注了你：")
					.append("http://127.0.0.1:8080/user/").append(eventModel.getActorId());
		}
		
		Message message = new Message(0, eventModel.getActorId(),
				eventModel.getEntityOwnerId(), new Date(), 0, content.toString());
		
		messageService.addMessage(message);
	}
	
	@Override
	public List<EventType> getRegisteredEventTypes() {
		return Arrays.asList(EventType.FOLLOW);
	}
}
