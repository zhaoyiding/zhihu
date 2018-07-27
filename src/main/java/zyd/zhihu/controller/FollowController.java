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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class FollowController {
	private static final Logger LOGGER = Logger.getLogger("FollowController");
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private HostHolder holder;
	
	@Autowired
	private EventProducer producer;
	
	@RequestMapping(value = {"/followUser"}, method = RequestMethod.POST)
	@ResponseBody
	public String followUser(@RequestParam("userId") int userId) {
		if (holder.get() == null) {
			LOGGER.severe("登录拦截器失效");
			return MyUtil.getJsonMsgStr(999, "未登录");
		}
		
		User localUser = holder.get();
		if (!followService.follow(localUser.getId(), EntityType.USER, userId)) {
			return MyUtil.getJsonMsgStr(1, "关注失败");
		}
		
		EventModel eventModel =
				new EventModel(EventType.FOLLOW,
						localUser.getId(), EntityType.USER, userId, userId);
		producer.fireEvent(eventModel);
		
		//返回关注对象的人数
		long followeeCount = followService.getFolloweeCount(localUser.getId(), EntityType.USER);
		return MyUtil.getJsonMsgStr(0, String.valueOf(followeeCount));
	}
	
	@RequestMapping(value = {"/unfollowUser"}, method = RequestMethod.POST)
	@ResponseBody
	public String unfollowUser(@RequestParam("userId") int userId) {
		if (holder.get() == null) {
			LOGGER.severe("登录拦截器失效");
			return MyUtil.getJsonMsgStr(999, "未登录");
		}
		
		User localUser = holder.get();
		if (!followService.unfollow(localUser.getId(), EntityType.USER, userId)) {
			return MyUtil.getJsonMsgStr(1, "取关失败");
		}
		
		//返回关注对象的人数
		long followeeCount = followService.getFolloweeCount(localUser.getId(), EntityType.USER);
		return MyUtil.getJsonMsgStr(0, String.valueOf(followeeCount));
	}
	
	@RequestMapping(value = {"/followQuestion"}, method = RequestMethod.POST)
	@ResponseBody
	public String followQuestion(@RequestParam("questionId") int questionId) {
		if (holder.get() == null) {
			LOGGER.severe("登录拦截器失效");
			return MyUtil.getJsonMsgStr(999, "未登录");
		}
		
		Question question = questionService.getQuestionById(questionId);
		if (question == null) {
			LOGGER.severe("问题不存在");
			return MyUtil.getJsonMsgStr(1, "问题不存在");
		}
		
		User localUser = holder.get();
		if (!followService.follow(localUser.getId(), EntityType.QUESTION, questionId)) {
			return MyUtil.getJsonMsgStr(1, "关注失败");
		}
		
		EventModel eventModel =
				new EventModel(EventType.FOLLOW,
						localUser.getId(), EntityType.QUESTION, questionId, question.getUserId());
		producer.fireEvent(eventModel);
		
		Map<String, Object> info = new HashMap<>();
		info.put("headUrl", localUser.getHeadUrl());
		info.put("name", localUser.getName());
		info.put("id", localUser.getId());
		
		//返回关注问题的人数
		info.put("count", followService.getFollowerCount(EntityType.QUESTION, questionId));
		return MyUtil.getJsonMsgStr(0, info);
	}
	
	@RequestMapping(value = {"/unfollowQuestion"}, method = RequestMethod.POST)
	@ResponseBody
	public String unfollowQuestion(@RequestParam("questionId") int questionId) {
		if (holder.get() == null) {
			LOGGER.severe("登录拦截器失效");
			return MyUtil.getJsonMsgStr(999, "未登录");
		}
		
		Question question = questionService.getQuestionById(questionId);
		if (question == null) {
			LOGGER.severe("问题不存在");
			return MyUtil.getJsonMsgStr(1, "问题不存在");
		}
		
		User localUser = holder.get();
		if (!followService.unfollow(localUser.getId(), EntityType.QUESTION, questionId)) {
			return MyUtil.getJsonMsgStr(1, "取关失败");
		}
		
		Map<String, Object> info = new HashMap<>();
//		info.put("headUrl",localUser.getHeadUrl());
//		info.put("name",localUser.getName());
		info.put("id", localUser.getId());
		
		//返回关注问题的人数
		info.put("count", followService.getFollowerCount(EntityType.QUESTION, questionId));
		return MyUtil.getJsonMsgStr(0, info);
	}
	
	@RequestMapping(value = {"/user/{userId}/followers"}, method = RequestMethod.GET)
	public String getFollowers(Model model, @PathVariable("userId") int userId) {
		if (holder.get() == null) {
			LOGGER.severe("登录拦截器失效");
			return MyUtil.getJsonMsgStr(999, "未登录");
		}
		
		User localUser = holder.get();
		List<Integer> followerId = followService.getFollowerId(EntityType.USER, userId, 0, 100);
		
		model.addAttribute("followers", getUserInfo(localUser.getId(), followerId));
		model.addAttribute("followerCount", followService.getFollowerCount(EntityType.USER, localUser.getId()));
		
		return "followers";
	}
	
	@RequestMapping(value = {"/user/{userId}/followees"}, method = RequestMethod.GET)
	public String getFollowees(Model model, @PathVariable("userId") int userId) {
		if (holder.get() == null) {
			LOGGER.severe("登录拦截器失效");
			return MyUtil.getJsonMsgStr(999, "未登录");
		}
		
		User localUser = holder.get();
		List<Integer> followeeId = followService.getFolloweeId(EntityType.USER, userId, 0, 100);
		
		model.addAttribute("followees", getUserInfo(localUser.getId(), followeeId));
		model.addAttribute("followeeCount", followService.getFollowerCount(EntityType.USER, localUser.getId()));
		
		return "followees";
	}
	
	private List<ViewObject> getUserInfo(int localUserId, List<Integer> followerId) {
		List<ViewObject> vos = new ArrayList<>();
		if (followerId == null) {
			return vos;
		}
		
		for (Integer uid : followerId) {
			User user = userService.getUserById(uid);
			if (user == null) {
				continue;
			}
			
			ViewObject vo = new ViewObject();
			vo.put("user", user);
			
			vo.put("followerCount", followService.getFollowerCount(EntityType.USER, uid));
			vo.put("followeeCount", followService.getFolloweeCount(uid, EntityType.USER));
			vo.put("commentCount", commentService.getCommentCountByUserId(uid));
			vo.put("likeCount", likeService.getLikeCount(EntityType.USER, uid));
			vo.put("followed", followService.isFollower(localUserId, EntityType.USER, uid));
			
			vos.add(vo);
		}
		return vos;
	}
}
