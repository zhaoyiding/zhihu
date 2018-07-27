package zyd.zhihu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zyd.zhihu.model.Feed;

import java.util.List;

@Repository
@Mapper
public interface FeedDao {
	int addFeed(@Param("feed") Feed feed);
	
	Feed getFeedById(@Param("id") int id);
	
	List<Feed> getFeedsByFolloweeIds(@Param("followeeIds") List<Integer> followeeIds,
	                                 @Param("count") int count);
	
	int insertSelective(@Param("feed") Feed feed);
	
	int insertList(@Param("feeds") List<Feed> feeds);
	
	int update(@Param("feed") Feed feed);
}
