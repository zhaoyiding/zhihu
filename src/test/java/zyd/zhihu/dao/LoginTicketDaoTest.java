package zyd.zhihu.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zyd.zhihu.ZhihuApplication;
import zyd.zhihu.model.LoginTicket;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhihuApplication.class)
public class LoginTicketDaoTest {
	
	@Autowired
	private LoginTicketDao ticketDao;
	
	@Test
	public void addTicket() throws Exception {
	}
	
	@Test
	public void getLoginTicketByTicket() throws Exception {
		LoginTicket ticket = ticketDao.getLoginTicketByTicket("2e423bbea31c4838b78d6e7566d29c37");
		System.out.println(ticket.getUserId());
	}
	
	@Test
	public void updateTicket() throws Exception {
		ticketDao.updateTicketStatus("a979aa7314a441e28c1eccccbcd873ed");
	}
	
}