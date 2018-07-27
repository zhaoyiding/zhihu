package zyd.zhihu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zyd.zhihu.model.EntityType;
import zyd.zhihu.model.Question;
import zyd.zhihu.model.ViewObject;
import zyd.zhihu.service.FollowService;
import zyd.zhihu.service.QuestionService;
import zyd.zhihu.service.SearchService;
import zyd.zhihu.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class SearchController {
	private static final Logger LOGGER = Logger.getLogger("SearchController");
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
	public String search(Model model, @RequestParam("q") String keyword,
	                     @RequestParam(value = "offset", defaultValue = "0") int offset,
	                     @RequestParam(value = "count", defaultValue = "10") int count) {
		try {
			List<Question> questionList = searchService.searchQuestion(keyword, offset, count);
			List<ViewObject> vos = new ArrayList<>();
			for (Question question : questionList) {
				Question q = questionService.getQuestionById(question.getId());
				ViewObject vo = new ViewObject();
				if (question.getContent() != null) {
					q.setContent(question.getContent());
				}
				if (question.getTitle() != null) {
					q.setTitle(question.getTitle());
				}
				vo.put("question", q);
				vo.put("followCount", followService.getFollowerCount(EntityType.QUESTION, question.getId()));
				vo.put("user", userService.getUserById(q.getUserId()));
				vos.add(vo);
			}
			model.addAttribute("vos", vos);
			model.addAttribute("keyword", keyword);
		} catch (Exception e) {
			LOGGER.severe("搜索评论失败" + e.getMessage());
		}
		return "result";
	}
}
