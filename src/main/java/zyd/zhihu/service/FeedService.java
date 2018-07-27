package zyd.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyd.zhihu.dao.FeedDao;
import zyd.zhihu.model.Feed;
import zyd.zhihu.utils.JedisAdapter;
import zyd.zhihu.utils.RedisKeyUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {
	
	@Autowired
	private FeedDao feedDao;
	
	@Autowired
	private JedisAdapter adapter;
	
	
	public int addFeed(Feed feed) {
		return feedDao.addFeed(feed);
	}
	
	public Feed getFeedById(int id) {
		return feedDao.getFeedById(id);
	}
	
	public List<Feed> getFeedsByFolloweeIds(List<Integer> followeeIds, int count) {
		return feedDao.getFeedsByFolloweeIds(followeeIds, count);
	}
	
	public List<Feed> getFeedsByUserId(int userId, int count) {
		String timelineKey = RedisKeyUtil.getTimeline(userId);
		
		List<String> feedIds = adapter.lrange(timelineKey, -1, -count);
		List<Feed> feeds = new ArrayList<>();
		if (feedIds != null) {
			for (String id : feedIds) {
				Feed f = getFeedById(Integer.parseInt(id));
				feeds.add(f);
			}
		}
		return feeds;
	}
}
