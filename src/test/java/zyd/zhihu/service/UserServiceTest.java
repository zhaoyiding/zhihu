package zyd.zhihu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	@Sql("/User.sql")
	public void addUser() throws Exception {
		for (int i = 0; i < 6; ++i) {
			User u = new User(0, "zyd" + i, "123456", "xxx",
					String.format("http://images.nowcoder.com/head/%dt.png", i));
		}
	}
	
	@Test
	public void getUserById() throws Exception {
		System.out.println(userService.getUserById(2));
	}
	
}