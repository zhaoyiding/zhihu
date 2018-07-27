package zyd.zhihu.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import zyd.zhihu.service.UserService;
import zyd.zhihu.utils.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class LoginController {
	
	private static final Logger LOGGER = Logger.getLogger("LoginController");
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/reglogin"}, method = RequestMethod.GET)
	public String reglogin(Model model,
	                       @RequestParam(name = "next", required = false) String next) {
		model.addAttribute("next", next);
		return "login";
	}
	
	@RequestMapping(value = {"/reg"}, method = RequestMethod.POST)
	public String register(@RequestParam("username") String username,
	                       @RequestParam("password") String password,
	                       @RequestParam(name = "rememberme", defaultValue = "false") boolean rememberme,
	                       @RequestParam(name = "next", required = false) String next,
	                       Model model,
	                       HttpServletResponse response) {
		try {
			Map<String, String> map = userService.register(username, password);
			if (map != null && map.containsKey(Constants.TICKET)) {
				Cookie cookie = new Cookie(Constants.TICKET, map.get(Constants.TICKET));
				cookie.setPath("/");
				if (rememberme == true) {
					cookie.setMaxAge(Constants.TICKET_LIVE_TIME_SECONDS);
				}
				response.addCookie(cookie);
				
				if (StringUtils.isNotBlank(next)) {
					return "redirect:" + next;
				}
				
				return "redirect:/";
			} else {
				model.addAttribute("msg", map.get("msg"));
				return "login";
			}
		} catch (Exception e) {
			LOGGER.severe("注册异常：" + e.getMessage());
			model.addAttribute("msg", "服务器错误");
			return "login";
		}
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
	                    @RequestParam("password") String password,
	                    @RequestParam(name = "rememberme", defaultValue = "false") boolean rememberme,
	                    @RequestParam(name = "next", required = false) String next,
	                    Model model,
	                    HttpServletResponse response) {
		try {
			Map<String, String> map = userService.login(username, password);
			if (map != null && map.containsKey(Constants.TICKET)) {
				Cookie cookie = new Cookie(Constants.TICKET, map.get(Constants.TICKET));
				cookie.setPath("/");
				if (rememberme == true) {
					cookie.setMaxAge(Constants.TICKET_LIVE_TIME_SECONDS);
				}
				response.addCookie(cookie);
				
				if (StringUtils.isNotBlank(next)) {
					return "redirect:" + next;
				}
				
				return "redirect:/";
			} else {
				model.addAttribute("msg", map.get("msg"));
				return "login";
			}
		} catch (Exception e) {
			LOGGER.severe("登录异常：" + e.getMessage());
			model.addAttribute("msg", "服务器错误");
			return "login";
		}
	}
	
	@RequestMapping(value = {"/logout"}, method = {RequestMethod.POST, RequestMethod.GET})
	public String logout(@CookieValue(Constants.TICKET) String ticket) {
		try {
			userService.logout(ticket);
		} catch (Exception e) {
			LOGGER.severe("退出异常：" + e.getMessage());
		}
		return "redirect:/";
	}
}
