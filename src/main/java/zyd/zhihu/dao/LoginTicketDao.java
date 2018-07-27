package zyd.zhihu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.springframework.stereotype.Repository;
import zyd.zhihu.model.LoginTicket;

@Mapper
@Repository
public interface LoginTicketDao {
    int addTicket(@Param("loginTicket") LoginTicket loginTicket);
    
    LoginTicket getLoginTicketByTicket(@Param("ticket") String ticket);

    int insertSelective(@Param("loginTicket") LoginTicket loginTicket);

    int insertList(@Param("loginTickets") List<LoginTicket> loginTickets);

    int updateTicketStatus(@Param("ticket") String ticket);
}
