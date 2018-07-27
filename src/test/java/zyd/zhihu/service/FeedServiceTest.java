package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.Feed;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class FeedServiceTest {
	@Autowired
	private FeedService feedService;
	
	@Test
	public void addFeed() throws Exception {
		Feed feed = new Feed(0, new Date(), 13, EntityType.COMMENT);
		feed.set("name", "zyd");
		feed.set("password", "12345");
		feed.set("title", "先有鸡还是先有蛋？");
		feedService.addFeed(feed);
		
		System.out.println(feed.getId());
	}
	
	@Test
	public void getFeedById() throws Exception {
		Feed feed = feedService.getFeedById(2);
		System.out.println(feed);
	}
	
	@Test
	public void getFolloweeFeed() throws Exception {
	}
	
}