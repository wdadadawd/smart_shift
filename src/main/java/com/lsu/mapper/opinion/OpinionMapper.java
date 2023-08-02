package com.lsu.mapper.opinion;

import com.lsu.entity.Opinion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 30567
* @description 针对表【opinion】的数据库操作Mapper
* @createDate 2023-07-31 18:37:38
* @Entity com.lsu.entity.Opinion
*/
public interface OpinionMapper extends BaseMapper<Opinion> {

    /**
     * 添加一个点赞
     * @param userId 用户id
     * @param opinionId 意见id
     */
    @Select("insert into like_map(user_id,opinion_id)) values (#{userId},#{opinionId}))")
    void insertLike(Integer userId,Integer opinionId);
}




