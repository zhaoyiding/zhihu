package zyd.zhihu.async;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import zyd.zhihu.utils.JedisAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static zyd.zhihu.utils.RedisKeyUtil.getEventQueueKey;

@Component
public class EventConsumer implements InitializingBean, ApplicationContextAware {
	private static final Logger LOGGER = Logger.getLogger("EventConsumer");
	
	private Map<EventType, List<EventHandler>> typeToHandlers = new HashMap<>();
	private ApplicationContext applicationContext;
	
	@Autowired
	private JedisAdapter adapter;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, EventHandler> nameToHandler = applicationContext.getBeansOfType(EventHandler.class);
		
		if (nameToHandler != null) {
			for (String name : nameToHandler.keySet()) {
				EventHandler handler = nameToHandler.get(name);
				List<EventType> types = handler.getRegisteredEventTypes();
				
				for (EventType type : types) {
					if (!typeToHandlers.containsKey(type)) {
						typeToHandlers.put(type, new ArrayList<>());
					}
					typeToHandlers.get(type).add(handler);
				}
			}
		}
		
		
		Thread t = new Thread(() -> {
			while (true) {
				String key = getEventQueueKey();
				List<String> list = adapter.blpop(0, key);
				
				//正常返回值包含队列名和头元素，长度为2
				if (list.size() != 2) {
					continue;
				}
				
				EventModel eventModel = JSONObject.parseObject(list.get(1), EventModel.class);
				if (!typeToHandlers.containsKey(eventModel.getType())) {
					LOGGER.severe("无法识别的事件类型：" + eventModel.getType());
					continue;
				}
				
				List<EventHandler> handlers = typeToHandlers.get(eventModel.getType());
				for (EventHandler handler : handlers) {
					handler.doHandle(eventModel);
				}
			}
		});
		t.start();
		
	}
	
	
}
