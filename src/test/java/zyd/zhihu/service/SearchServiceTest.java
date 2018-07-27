package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.Question;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class SearchServiceTest {
	@Autowired
	private SearchService searchService;
	
	@Test
	public void searchQuestion() throws Exception {
		List<Question> list = searchService.searchQuestion("习近平", 0, 10);
	}
	
	@Test
	public void indexQuestion() throws Exception {
	
	}
	
}