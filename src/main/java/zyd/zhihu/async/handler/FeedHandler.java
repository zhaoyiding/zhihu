package zyd.zhihu.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zyd.zhihu.async.EventHandler;
import zyd.zhihu.async.EventModel;
import zyd.zhihu.async.EventType;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.Feed;
import zyd.zhihu.model.Question;
import zyd.zhihu.model.User;
import zyd.zhihu.service.FeedService;
import zyd.zhihu.service.FollowService;
import zyd.zhihu.service.QuestionService;
import zyd.zhihu.service.UserService;
import zyd.zhihu.utils.JedisAdapter;
import zyd.zhihu.utils.RedisKeyUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FeedHandler implements EventHandler {
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private JedisAdapter adapter;
	
	@Override
	public void doHandle(EventModel eventModel) {
		User actor = userService.getUserById(eventModel.getActorId());
		if (actor == null) {
			return;
		}
		
		Question question = questionService.getQuestionById(eventModel.getEntityId());
		if (question == null) {
			return;
		}
		
		Feed feed = new Feed();
		feed.setCreatedTime(new Date());
		feed.setUserId(eventModel.getActorId());
		feed.setType(eventModel.getType().getValue());
		
		feed.set("userId", String.valueOf(actor.getId()));
		feed.set("userName", actor.getName());
		feed.set("userHeadUrl", actor.getHeadUrl());
		
		feed.set("questionId", String.valueOf(question.getId()));
		feed.set("questionTitle", question.getTitle());
		
		feedService.addFeed(feed);
		
		//推模式下，发了新鲜事后向所有粉丝广播新鲜事
		broadcast(actor, feed);
	}
	
	/*
	* 推模式下，发了新鲜事后,将feedId加入所有粉丝的时间线队列中
	* */
	private void broadcast(User actor, Feed feed) {
		List<Integer> followeeIds = followService.getFolloweeId(actor.getId(), EntityType.USER, 0, Integer.MAX_VALUE);
		for (Integer i : followeeIds) {
			String timelineKey = RedisKeyUtil.getTimeline(i);
			adapter.rpush(timelineKey, String.valueOf(feed.getId()));
		}
	}
	
	@Override
	public List<EventType> getRegisteredEventTypes() {
		return Arrays.asList(EventType.COMMENT);
	}
}
