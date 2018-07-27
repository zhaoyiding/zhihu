package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zyd.zhihu.model.HostHolder;
import zyd.zhihu.model.Message;
import zyd.zhihu.model.User;
import zyd.zhihu.model.ViewObject;
import zyd.zhihu.service.MessageService;
import zyd.zhihu.service.UserService;
import zyd.zhihu.utils.Constants;
import zyd.zhihu.utils.MyUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class MessageController {
	private static final Logger LOGGER = Logger.getLogger("MessageController");
	
	@Autowired
	private MessageService ms;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private HostHolder holder;
	
	@RequestMapping(value = {"/msg/detail"}, method = RequestMethod.GET)
	public String getConversationDetail(@RequestParam("conversationId") String conversationId,
	                                    Model model) {
		try {
			if (holder.get() == null) {
				LOGGER.severe("查看对话详情拦截器失效");
				return "redirect:/";
			}
			
			List<Message> messages = ms.getConversationDetail(conversationId, 0, Constants.NUM_OF_MESSAGES_PER_PAGE);
			if (messages != null) {
				List<ViewObject> vos = new ArrayList<>();
				for (Message m : messages) {
					ViewObject vo = new ViewObject();
					
					//把消息设为已读
					ms.setMessageHasRead(m.getId(), 1);
					vo.put("message", m);
					User u = us.getUserById(m.getFromId());
					if (u != null) {
						vo.put("user", u);
					}
					
					vos.add(vo);
				}
				
				model.addAttribute("messages", vos);
			}
			
		} catch (Exception e) {
			LOGGER.severe("查看对话详情失败：" + e.getMessage());
		}
		return "letterDetail";
	}
	
	@RequestMapping(value = {"/msg/list"}, method = RequestMethod.GET)
	public String getConversationList(Model model) {
		try {
			if (holder.get() == null) {
				LOGGER.severe("查看对话列表拦截器失效");
				return "redirect:/";
			}
			int userId = holder.get().getId();
			List<Message> conversations = ms.getConversationList(userId, 0, Constants.NUM_OF_MESSAGES_PER_PAGE);
			if (conversations != null) {
				List<ViewObject> vos = new ArrayList<>();
				for (Message m : conversations) {
					ViewObject vo = new ViewObject();
					
					vo.put("message", m);
					int targetId = userId == m.getFromId() ? m.getToId() : m.getFromId();
					User u = us.getUserById(targetId);
					if (u != null) {
						vo.put("user", u);
					}
					vo.put("unread", ms.getConversationUnreadCount(m.getConversationId()));
					vo.put("count", ms.getMessageCount(m.getConversationId()));
					
					vos.add(vo);
				}
				
				model.addAttribute("conversations", vos);
			}
			
		} catch (Exception e) {
			LOGGER.severe("查看对话详情失败：" + e.getMessage());
		}
		return "letter";
	}
	
	@RequestMapping(value = {"/msg/add"}, method = RequestMethod.POST)
	@ResponseBody
	public String addMessage(@RequestParam("toName") String toName,
	                         @RequestParam("content") String content
	) {
		try {
			if (holder.get() == null) {
				LOGGER.severe("发消息拦截器失效");
				return "redirect:/";
			}
			User toUser = us.getUserByName(toName);
			if (toUser == null) {
				return MyUtil.getJsonMsgStr(1, "该用户不存在");
			}
			int fromId = holder.get().getId();
			int toId = toUser.getId();
			Message message = new Message(0, fromId, toId, new Date(), 0, content);
			ms.addMessage(message);
			
			return MyUtil.getJsonMsgStr(0, "成功");
		} catch (Exception e) {
			LOGGER.severe("发消息失败：" + e.getMessage());
			return MyUtil.getJsonMsgStr(1, "发消息失败");
		}
		
	}
}
