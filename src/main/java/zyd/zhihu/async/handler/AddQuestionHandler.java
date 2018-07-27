package zyd.zhihu.async.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zyd.zhihu.async.EventHandler;
import zyd.zhihu.async.EventModel;
import zyd.zhihu.async.EventType;
import zyd.zhihu.service.SearchService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class AddQuestionHandler implements EventHandler {
	private static final Logger LOGGER = Logger.getLogger("AddQuestionHandler");
	@Autowired
	private SearchService searchService;
	
	@Override
	public void doHandle(EventModel model) {
		try {
			searchService.indexQuestion(model.getEntityId(),
					model.get("title"), model.get("content"));
		} catch (Exception e) {
			LOGGER.severe("增加题目索引失败");
		}
	}
	
	@Override
	public List<EventType> getRegisteredEventTypes() {
		return Arrays.asList(EventType.ADD_QUESTION);
	}
	
	
}
