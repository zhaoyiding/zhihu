package zyd.zhihu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zyd.zhihu.model.Question;

import java.util.List;

@Mapper
@Repository
public interface QuestionDao {
	int addQuestion(@Param("question") Question question);
	
	int insertSelective(@Param("question") Question question);
	
	int insertList(@Param("questions") List<Question> questions);
	
	int update(@Param("question") Question question);
	
	List<Question> getLatestQuestions(@Param("userId") int userId,
	                                  @Param("offset") int offset,
	                                  @Param("limit") int limit);
	
	Question getQuestionById(@Param("id") int id);
	
	int updateCommentCountById(@Param("id") int id, @Param("increment") int increment);
}
