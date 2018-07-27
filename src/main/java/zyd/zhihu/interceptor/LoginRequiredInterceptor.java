package zyd.zhihu.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zyd.zhihu.model.HostHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		if (hostHolder.get() == null) {
			httpServletResponse.sendRedirect("/reglogin?next=" + httpServletRequest.getRequestURI());
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
	
	}
	
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
	
	}
}
