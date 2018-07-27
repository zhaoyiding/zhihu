package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zyd.zhihu.async.EventModel;
import zyd.zhihu.async.EventProducer;
import zyd.zhihu.async.EventType;
import zyd.zhihu.model.Comment;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.HostHolder;
import zyd.zhihu.service.CommentService;
import zyd.zhihu.service.QuestionService;

import java.util.Date;
import java.util.logging.Logger;

@Controller
public class CommentController {
	private static final Logger LOGGER = Logger.getLogger("CommentController");
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private EventProducer producer;
	
	@RequestMapping(value = {"/comment/add"}, method = RequestMethod.POST)
	public String addComment(@RequestParam("questionId") int questionId,
	                         @RequestParam("content") String content) {
		try {
			int userId = 0;
			if (hostHolder.get() == null) {
				LOGGER.severe("评论拦截器失效");
				return "redirect:/reglogin";
			} else {
				userId = hostHolder.get().getId();
			}
			
			Comment comment = new Comment(0, userId, new Date(), EntityType.QUESTION, questionId, 0, content);
			if (commentService.addComment(comment) > 0) {
				//更新question表中存储的评论数，需要同步，可以利用mysql的行级锁
				questionService.updateCommentCountById(questionId, 1);
				
				//发出评论事件，这里FeedHandler没有用到OwnerId，减少对表的读取
				producer.fireEvent(new EventModel(EventType.COMMENT, userId, EntityType.QUESTION, questionId, 0));
			}
		} catch (Exception e) {
			LOGGER.severe("评论失败：" + e.getMessage());
		}
		return "redirect:/question/" + questionId;
	}
	
}
