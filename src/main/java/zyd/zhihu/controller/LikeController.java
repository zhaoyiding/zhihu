package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zyd.zhihu.async.EventModel;
import zyd.zhihu.async.EventProducer;
import zyd.zhihu.async.EventType;
import zyd.zhihu.model.Comment;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.HostHolder;
import zyd.zhihu.service.CommentService;
import zyd.zhihu.service.LikeService;
import zyd.zhihu.utils.MyUtil;

import java.util.logging.Logger;

@Controller
public class LikeController {
	private static final Logger LOGGER = Logger.getLogger("LikeController");
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private HostHolder holder;
	
	@Autowired
	private EventProducer producer;
	
	@RequestMapping(value = {"/like"}, method = {RequestMethod.POST})
	@ResponseBody
	public String like(@RequestParam("commentId") int commentId) {
		try {
			if (holder.get() == null) {
				return MyUtil.getJsonMsgStr(999, "未登录无法点赞");
			}
			
			Comment comment = commentService.getCommentById(commentId);
			EventModel eventModel = new EventModel(EventType.LIKE,
					holder.get().getId(), EntityType.COMMENT, commentId, comment.getUserId());
			eventModel.put("questionId", String.valueOf(comment.getEntityId()));
			producer.fireEvent(eventModel);
			
			long likeCount = likeService.like(EntityType.COMMENT, commentId, holder.get().getId());
			return MyUtil.getJsonMsgStr(0, String.valueOf(likeCount));
		} catch (Exception e) {
			LOGGER.severe("点赞失败：" + e.getMessage());
			return MyUtil.getJsonMsgStr(1, "点赞失败");
		}
	}
	
	@RequestMapping(value = {"/dislike"}, method = {RequestMethod.POST})
	@ResponseBody
	public String dislike(@RequestParam("commentId") int commentId) {
		try {
			if (holder.get() == null) {
				return MyUtil.getJsonMsgStr(999, "未登录无法点踩");
			}
			
			long likeCount = likeService.dislike(EntityType.COMMENT, commentId, holder.get().getId());
			return MyUtil.getJsonMsgStr(0, String.valueOf(likeCount));
		} catch (Exception e) {
			LOGGER.severe("点踩失败：" + e.getMessage());
			return MyUtil.getJsonMsgStr(1, "点踩失败");
		}
	}
}
