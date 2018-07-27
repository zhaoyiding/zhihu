package zyd.zhihu.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zyd.zhihu.dao.LoginTicketDao;
import zyd.zhihu.dao.UserDao;
import zyd.zhihu.model.HostHolder;
import zyd.zhihu.model.LoginTicket;
import zyd.zhihu.model.User;
import zyd.zhihu.utils.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {
	
	@Autowired
	private LoginTicketDao ticketDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		String ticket = null;
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(Constants.TICKET)) {
					ticket = c.getValue();
					break;
				}
			}
		}
		
		if (StringUtils.isNotBlank(ticket)) {
			LoginTicket loginTicket = ticketDao.getLoginTicketByTicket(ticket);
			if (loginTicket != null &&
					loginTicket.getExpired().after(new Date()) &&
					loginTicket.getStatus() == 0) {
				User user = userDao.getUserById(loginTicket.getUserId());
				if (user != null) {
					hostHolder.set(user);
				}
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null &&
				hostHolder.get() != null) {
			modelAndView.addObject("user", hostHolder.get());
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		hostHolder.remove();
	}
}
