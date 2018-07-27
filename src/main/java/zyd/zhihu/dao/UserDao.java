package zyd.zhihu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.springframework.stereotype.Repository;
import zyd.zhihu.model.User;

@Mapper
@Repository
public interface UserDao {
    
    int addUser(@Param("user") User user);

    int insertSelective(@Param("user") User user);

    int insertList(@Param("users") List<User> users);

    int update(@Param("user") User user);
    
    User getUserById(@Param("userId") int userId);
    
    User getUserByName(@Param("name") String name);
    
}
