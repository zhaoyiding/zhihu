package zyd.zhihu.model;

import java.util.Date;

public class Question {
    private Integer id;

    private String title;

    private Integer userId;

    private Date createdTime;

    private Integer commentCount;

    private String content;

    public Question(Integer id, String title, Integer userId, Date createdTime, Integer commentCount, String content) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.createdTime = createdTime;
        this.commentCount = commentCount;
        this.content = content;
    }

    public Question() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}