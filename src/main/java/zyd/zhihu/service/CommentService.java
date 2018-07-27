package zyd.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyd.zhihu.dao.CommentDao;
import zyd.zhihu.model.Comment;
import zyd.zhihu.utils.SensitiveWordTool;

import java.util.List;

@Service
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private SensitiveWordTool tool;
	
	public int addComment(Comment comment) {
		//评论过滤html标签和敏感词
		comment.setContent(tool.checkText(comment.getContent()));
		return commentDao.addComment(comment);
	}
	
	public int updateStatus(int id, int status) {
		return commentDao.updateStatus(id, status);
	}
	
	public List<Comment> getCommentsByEntity(int entityType, int entityId) {
		return commentDao.getCommentsByEntity(entityType, entityId);
	}
	
	public int getCommentCountByEntity(int entityType, int entityId) {
		return commentDao.getCommentCountByEntity(entityType, entityId);
	}
	
	public int getCommentCountByUserId(int userId) {
		return commentDao.getCommentCountByUserId(userId);
	}
	
	public List<Comment> getCommentsByUserId(int userId) {
		return commentDao.getCommentsByUserId(userId);
	}
	
	public int delComment(int commentId) {
		return commentDao.updateStatus(commentId, 1);
	}
	
	public Comment getCommentById(int id) {
		return commentDao.getCommentById(id);
	}
}
