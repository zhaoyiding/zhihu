package zyd.zhihu.model;

import java.util.Date;

public class LoginTicket {
    
    private Integer id;

    private Integer userId;

    private Date expired;

    private String ticket;

    private Integer status;

    public LoginTicket(Integer id, Integer userId, Date expired, String ticket, Integer status) {
        this.id = id;
        this.userId = userId;
        this.expired = expired;
        this.ticket = ticket;
        this.status = status;
    }

    public LoginTicket() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}