package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zyd.zhihu.async.EventModel;
import zyd.zhihu.async.EventProducer;
import zyd.zhihu.async.EventType;
import zyd.zhihu.model.*;
import zyd.zhihu.service.*;
import zyd.zhihu.utils.MyUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class QuestionController {
	
	private static final Logger LOGGER = Logger.getLogger("QuestionController");
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private EventProducer producer;
	
	@RequestMapping(value = {"/question/add"}, method = RequestMethod.POST)
	@ResponseBody
	public String addQuestion(@RequestParam("title") String title,
	                          @RequestParam("content") String content) {
		try {
			int userId = 0;
			if (hostHolder.get() == null) {
				LOGGER.severe("提交问题登录拦截器失效");
				//ajax对于999会跳转到首页
				return MyUtil.getJsonMsgStr(999, "未登录");
			} else {
				userId = hostHolder.get().getId();
			}
			
			Question question = new Question(0, title, userId, new Date(), 0, content);
			
			if (questionService.addQuestion(question) > 0) {
				EventModel model = new EventModel(EventType.ADD_QUESTION,
						userId, EntityType.QUESTION, question.getId(), userId);
				model.put("title", question.getTitle());
				model.put("content", question.getContent());
				
				producer.fireEvent(model);
				
				return MyUtil.getJsonMsgStr(0, "发布问题成功");
			}
		} catch (Exception e) {
			LOGGER.severe("发布问题失败：" + e.getMessage());
		}
		return MyUtil.getJsonMsgStr(1, "发布问题失败");
	}
	
	@RequestMapping(value = {"/question/{questionId}"}, method = RequestMethod.GET)
	public String getQuestionDetail(@PathVariable("questionId") int questionId,
	                                Model model) {
		try {
			Question question = questionService.getQuestionById(questionId);
			model.addAttribute("question", question);
			
			List<Comment> comments = commentService.getCommentsByEntity(EntityType.QUESTION, questionId);
			if (comments != null) {
				List<ViewObject> vos = new ArrayList<>();
				for (Comment c : comments) {
					ViewObject vo = new ViewObject();
					
					vo.put("comment", c);
					User u = userService.getUserById(c.getUserId());
					if (u != null) {
						vo.put("user", u);
					}
					
					if (hostHolder.get() == null) {
						vo.put("liked", 0);
					} else {
						vo.put("liked", likeService.getLikeStatus(EntityType.COMMENT, c.getId(), hostHolder.get().getId()));
					}
					
					vo.put("likeCount", likeService.getLikeCount(EntityType.COMMENT, c.getId()));
					
					vos.add(vo);
				}
				
				model.addAttribute("comments", vos);
			}
			
			List<Integer> followerId = followService.getFollowerId(EntityType.QUESTION, questionId, 0, 10);
			if (followerId != null) {
				List<ViewObject> vos = new ArrayList<>();
				
				for (Integer uid : followerId) {
					User user = userService.getUserById(uid);
					if (user == null) {
						continue;
					}
					
					ViewObject vo = new ViewObject();
					vo.put("name", user.getName());
					vo.put("id", user.getId());
					vo.put("headUrl", user.getHeadUrl());
				}
				
				model.addAttribute("followUsers", vos);
			}
			
			if (hostHolder.get() == null) {
				model.addAttribute("followed", false);
			} else {
				model.addAttribute("followed",
						followService.isFollower(
								hostHolder.get().getId(), EntityType.QUESTION, questionId));
			}
			
		} catch (Exception e) {
			LOGGER.severe("查看问题失败：" + e.getMessage());
		}
		return "detail";
	}
}
