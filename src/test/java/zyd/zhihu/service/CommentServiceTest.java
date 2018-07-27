package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.Comment;
import zyd.zhihu.model.EntityType;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class CommentServiceTest {
	@Autowired
	private CommentService commentService;
	
	@Test
	public void getCommentById() throws Exception {
		System.out.println(commentService.getCommentById(1));
	}
	
	@Test
	public void addComment() throws Exception {
		commentService.addComment(new Comment(0, 10, new Date(), EntityType.QUESTION, 1, 0, "你来说说这是为什么呢"));
		commentService.addComment(new Comment(0, 10, new Date(), EntityType.QUESTION, 2, 0, "楼下接着"));
		commentService.addComment(new Comment(0, 10, new Date(), EntityType.QUESTION, 3, 0, "楼主傻逼"));
		commentService.addComment(new Comment(0, 10, new Date(), EntityType.QUESTION, 4, 0, "抢沙发"));
	}
	
	@Test
	public void updateStatus() throws Exception {
		commentService.updateStatus(1, 1);
	}
	
	@Test
	public void getCommentsByEntity() throws Exception {
		List<Comment> comments = commentService.getCommentsByEntity(EntityType.QUESTION, 1);
		System.out.println(comments.size());
	}
	
	@Test
	public void getCommentCountByEntity() throws Exception {
		System.out.println(commentService.getCommentCountByEntity(EntityType.QUESTION, 1));
	}
	
	@Test
	public void getCommentCountByUserId() throws Exception {
		System.out.println(commentService.getCommentCountByUserId(10));
	}
	
	@Test
	public void getCommentsByUserId() throws Exception {
		List<Comment> comments = commentService.getCommentsByUserId(10);
		System.out.println(comments.size());
	}
	
}