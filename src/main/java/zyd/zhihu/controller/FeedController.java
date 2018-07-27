package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.Feed;
import zyd.zhihu.model.HostHolder;
import zyd.zhihu.service.FeedService;
import zyd.zhihu.service.FollowService;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class FeedController {
	private static final Logger LOGGER = Logger.getLogger("FeedController");
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private HostHolder holder;
	
	
	/*
	* 拉模式
	* */
//	@RequestMapping(value = {"/feeds"}, method = {RequestMethod.GET})
	public String pullFeeds(Model model) {
		int userId = 0;
		if (holder.get() != null) {
			userId = holder.get().getId();
		}
		
		List<Integer> followees = followService.getFolloweeId(userId, EntityType.USER, 0, 100);
		List<Feed> feeds = feedService.getFeedsByFolloweeIds(followees, 10);
		model.addAttribute("feeds", feeds);
		
		return "feeds";
	}
	
	/*
	* 推模式
	* */
	@RequestMapping(value = {"/feeds"}, method = {RequestMethod.GET})
	public String pushFeeds(Model model) {
		int userId = 0;
		if (holder.get() != null) {
			userId = holder.get().getId();
		}
		
		List<Feed> feeds = feedService.getFeedsByUserId(userId, 10);
		model.addAttribute("feeds", feeds);
		return "feeds";
	}
}
