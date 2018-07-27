package zyd.zhihu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zyd.zhihu.model.Comment;

import java.util.List;
@Repository
@Mapper
public interface CommentDao {
	int addComment(@Param("comment") Comment comment);
	
	int insertSelective(@Param("comment") Comment comment);
	
	int insertList(@Param("comments") List<Comment> comments);
	
	int updateStatus(@Param("id") int id,@Param("status") int status);
	
	List<Comment> getCommentsByEntity(@Param("entityType") int entityType,
	                                  @Param("entityId") int entityId);
	
	int getCommentCountByEntity(@Param("entityType") int entityType,
	                                  @Param("entityId") int entityId);
	
	int getCommentCountByUserId(@Param("userId") int userId);
	
	List<Comment> getCommentsByUserId(@Param("userId") int userId);
	
	Comment getCommentById(@Param("id") int id);
	
}
