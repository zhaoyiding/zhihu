package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.EntityType;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class LikeServiceTest {
	@Autowired
	private LikeService likeService;
	
	@Test
	public void like() throws Exception {
		System.out.println(likeService.like(EntityType.COMMENT, 1, 10));
		System.out.println(likeService.like(EntityType.COMMENT, 2, 10));
		System.out.println(likeService.like(EntityType.COMMENT, 3, 10));
	}
	
	@Test
	public void dislike() throws Exception {
		System.out.println(likeService.dislike(EntityType.COMMENT, 4, 10));
		System.out.println(likeService.dislike(EntityType.COMMENT, 5, 10));
		System.out.println(likeService.dislike(EntityType.COMMENT, 6, 10));
	}
	
	@Test
	public void getLikeCount() throws Exception {
		System.out.println(likeService.getLikeCount(1, 1));
	}
	
	@Test
	public void getLikeStatus() throws Exception {
		System.out.println(likeService.getLikeStatus(1, 1, 10));
	}
	
}