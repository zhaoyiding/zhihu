package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zyd.zhihu.model.*;
import zyd.zhihu.service.*;
import zyd.zhihu.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class IndexController {
	
	private static final Logger LOGGER = Logger.getLogger("IndexController");
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private HostHolder holder;
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("vos", getViewObjectList(0, 0, Constants.NUM_OF_QUESTIONS_PER_PAGE));
		return "index";
	}
	
	@RequestMapping(value = {"/user/{userId}"}, method = RequestMethod.GET)
	public String userIndex(Model model, @PathVariable("userId") int userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			//用户的个人账户
			ViewObject vo = new ViewObject();
			vo.put("user", user);
			vo.put("followerCount", followService.getFollowerCount(EntityType.USER, userId));
			vo.put("followeeCount", followService.getFolloweeCount(userId, EntityType.USER));
			vo.put("commentCount", commentService.getCommentCountByUserId(userId));
			vo.put("likeCount", likeService.getLikeCount(EntityType.USER, userId));
			
			//我是否关注了当前用户
			if (holder.get() == null) {
				vo.put("followed", false);
			} else {
				vo.put("followed", followService.isFollower(holder.get().getId(), EntityType.USER, userId));
			}
			
			model.addAttribute("profileUser", vo);
			
			//用户提的问题
			model.addAttribute("vos", getViewObjectList(userId, 0, Constants.NUM_OF_QUESTIONS_PER_PAGE));
		}
		return "profile";
	}
	
	private List<ViewObject> getViewObjectList(int userId, int offset, int limit) {
		List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
		List<ViewObject> viewObjectList = new ArrayList<>();
		
		for (Question question : questionList) {
			ViewObject vo = new ViewObject();
			
			User user = userService.getUserById(question.getUserId());
			vo.put("user", user);
			vo.put("question", question);
			vo.put("followCount", followService.getFollowerCount(EntityType.QUESTION, question.getId()));
			viewObjectList.add(vo);
		}
		return viewObjectList;
	}
	
	
}
