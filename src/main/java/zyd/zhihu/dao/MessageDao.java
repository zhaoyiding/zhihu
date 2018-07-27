package zyd.zhihu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.springframework.stereotype.Repository;
import zyd.zhihu.model.Message;
@Repository
@Mapper
public interface MessageDao {
    int addMessage(@Param("message") Message message);

    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);
    
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                            @Param("offset") int offset,
                                            @Param("limit") int limit);
    
    int getConversationUnreadCount(@Param("conversationId") String conversationId);
    
    int getMessageCount(@Param("conversationId") String conversationId);
    
    int setMessageHasRead(@Param("id") int id, @Param("hasRead") int hasRead);
}
