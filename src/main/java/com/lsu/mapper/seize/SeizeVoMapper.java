package com.lsu.mapper.seize;

import com.lsu.entity.SeizeTopInfo;
import com.lsu.vo.SeizeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 30567
* @description 针对表【seize_vo】的数据库操作Mapper
* @createDate 2023-07-20 18:33:32
* @Entity com.lsu.vo.SeizeVo
*/
public interface SeizeVoMapper extends BaseMapper<SeizeVo> {

    @Select("SELECT seize_vo.staff_name,COUNT(*) AS seizeCount,sum(final_premiums) as premiums\n" +
            "FROM seize_vo\n" +
            "WHERE staff_id IS NOT NULL \n" +
            "AND  DATE_FORMAT(take_time, '%Y-%m') = DATE_FORMAT(#{date}, '%Y-%m')\n" +
            "AND is_accomplish = 1\n" +
            "GROUP BY staff_id\n" +
            "ORDER BY seizeCount DESC;")
    //获取抢单排行榜
    List<SeizeTopInfo> getSeizeTopInfos(@Param("date") String date);
}




