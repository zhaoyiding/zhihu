package zyd.zhihu.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
	private Map<String, Object> map = new HashMap<>(4);
	
	public void put(String key, Object object) {
		map.put(key, object);
	}
	
	public Object get(String key) {
		return map.get(key);
	}
}
