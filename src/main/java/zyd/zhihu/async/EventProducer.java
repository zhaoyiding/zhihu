package zyd.zhihu.async;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zyd.zhihu.utils.JedisAdapter;

import static zyd.zhihu.utils.RedisKeyUtil.getEventQueueKey;

@Component
public class EventProducer {
	
	@Autowired
	private JedisAdapter adapter;
	
	public boolean fireEvent(EventModel eventModel) {
		try {
			String json = JSONObject.toJSONString(eventModel);
			String key = getEventQueueKey();
			adapter.rpush(key, json);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
