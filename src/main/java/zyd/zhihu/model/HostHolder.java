package zyd.zhihu.model;

import org.springframework.stereotype.Component;
import zyd.zhihu.model.User;

@Component
public class HostHolder {
	private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();
	
	public void set(User user) {
		USER_THREAD_LOCAL.set(user);
	}
	
	public User get() {
		return USER_THREAD_LOCAL.get();
	}
	
	public void remove() {
		USER_THREAD_LOCAL.remove();
	}
}
