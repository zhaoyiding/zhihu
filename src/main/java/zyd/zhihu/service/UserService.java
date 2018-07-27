package zyd.zhihu.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zyd.zhihu.dao.LoginTicketDao;
import zyd.zhihu.dao.UserDao;
import zyd.zhihu.model.LoginTicket;
import zyd.zhihu.model.User;
import zyd.zhihu.utils.Constants;
import zyd.zhihu.utils.MyUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginTicketDao ticketDao;
	
	public Map<String, String> register(String name, String password) {
		Map<String, String> map = new HashMap<>(2);
		
		if (StringUtils.isBlank(name)) {
			map.put("msg", "用户名不能为空");
			return map;
		}
		
		if (StringUtils.isBlank(password)) {
			map.put("msg", "密码不能为空");
			return map;
		}
		
		User user = userDao.getUserByName(name);
		if (user != null) {
			map.put("msg", "用户名已被注册");
			return map;
		}
		
		
		String salt = MyUtil.getSalt();
		String newPassword = MyUtil.MD5(password + salt);
		user = new User(0, name, newPassword, salt, MyUtil.getHeadUrl());
		
		//将userId和ticket关联
		addUser(user);
		String ticket = addTicket(user.getId());
		map.put(Constants.TICKET, ticket);
		
		return map;
	}
	
	private String addTicket(int userId) {
		String ticket = MyUtil.getTicket();
		Date date = new Date();
		date.setTime(date.getTime() + Constants.TICKET_LIVE_TIME_MILLISECONDS);
		
		LoginTicket loginTicket = new LoginTicket(0, userId, date, ticket, 0);
		ticketDao.addTicket(loginTicket);
		return ticket;
	}
	
	/*
	* 插入新user后，传回数据库中自动生成的id,新id保存在user.id中
	* */
	private int addUser(User user) {
		return userDao.addUser(user);
	}
	
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}
	
	public Map<String, String> login(String name, String password) {
		Map<String, String> map = new HashMap<>(2);
		
		if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
			map.put("msg", "用户名或密码不能为空");
			return map;
		}
		
		User user = userDao.getUserByName(name);
		if (user == null) {
			map.put("msg", "用户名不存在");
			return map;
		}
		if (!MyUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
			map.put("msg", "用户名或密码不正确");
			return map;
		}
		
		//将userId和ticket关联
		String ticket = addTicket(user.getId());
		map.put(Constants.TICKET, ticket);
		
		return map;
	}
	
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	public void logout(String ticket) {
		ticketDao.updateTicketStatus(ticket);
	}
	
}
