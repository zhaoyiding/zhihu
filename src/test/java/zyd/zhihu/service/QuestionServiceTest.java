package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.Question;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class QuestionServiceTest {
	@Autowired
	private QuestionService questionService;
	
	@Test
	public void getLatestQuestions() throws Exception {
		System.out.println(questionService.getLatestQuestions(100, 0, 100).size());
	}
	
	@Test
	public void updateCommentCountById() throws Exception {
		questionService.updateCommentCountById(1, 1);
	}
	
	@Test
	@Sql("/Question.sql")
	public void addQuestion() throws Exception {
	}
	
	@Test
	public void getQuestions() throws Exception {
		List<Question> list = questionService.getLatestQuestions(0, 0, 100);
		Question question = list.get(0);
	}
	
	@Test
	public void getQuestionById() throws Exception {
		Question question = questionService.getQuestionById(2);
		System.out.println(question.getContent());
	}
	
}