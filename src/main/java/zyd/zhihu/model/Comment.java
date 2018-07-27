package zyd.zhihu.model;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer userId;

    private Date createdTime;

    private Integer entityType;

    private Integer entityId;

    private Integer status;

    private String content;

    public Comment(Integer id, Integer userId, Date createdTime, Integer entityType, Integer entityId, Integer status, String content) {
        this.id = id;
        this.userId = userId;
        this.createdTime = createdTime;
        this.entityType = entityType;
        this.entityId = entityId;
        this.status = status;
        this.content = content;
    }

    public Comment() {
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}