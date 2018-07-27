package zyd.zhihu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyd.zhihu.dao.QuestionDao;
import zyd.zhihu.model.Question;
import zyd.zhihu.utils.SensitiveWordTool;

import java.util.List;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private SensitiveWordTool tool;
	
	public int addQuestion(Question question) {
		question.setTitle(tool.checkText(question.getTitle()));
		question.setContent(tool.checkText(question.getContent()));
		
		return questionDao.addQuestion(question);
	}
	
	public List<Question> getLatestQuestions(int userId, int offset, int limit) {
		return questionDao.getLatestQuestions(userId, offset, limit);
	}
	
	public Question getQuestionById(int id) {
		return questionDao.getQuestionById(id);
	}
	
	public int updateCommentCountById(int id, int increment) {
		return questionDao.updateCommentCountById(id, increment);
	}
	
}
