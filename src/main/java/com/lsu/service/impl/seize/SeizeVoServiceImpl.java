package com.lsu.service.impl.seize;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.SeizeTopInfo;
import com.lsu.entity.StaffInfo;
import com.lsu.vo.SeizeVo;
import com.lsu.service.SeizeVoService;
import com.lsu.mapper.seize.SeizeVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【seize_vo】的数据库操作Service实现
* @createDate 2023-07-20 18:33:32
*/
@Service
public class SeizeVoServiceImpl extends ServiceImpl<SeizeVoMapper, SeizeVo>
    implements SeizeVoService{

    @Resource
    private SeizeVoMapper seizeVoMapper;

    /**
     * 获取分页的抢单信息
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @param storeId 门店id
     * @return
     */
    @Override
    public Page<SeizeVo> getSeizeVoPage(Integer current, Integer size, String key, Integer storeId) {
        Page<SeizeVo> page = new Page<>(current,size);
        QueryWrapper<SeizeVo> queryWrapper = new QueryWrapper<>();
        if (storeId!=null)                                          //判断是否筛选门店
            queryWrapper.eq("store_id",storeId);
        queryWrapper.and(q -> q.like("staff_name",key)        //设置查询条件
                .or().like("status",key)
                .or().like("lose_position",key) //对缺失岗位、员工名称、状态进行模糊查询
        );
        queryWrapper.last("order by case when status = '未发布' then 1 else 2 end, start_time asc");
        return seizeVoMapper.selectPage(page,queryWrapper);
    }

    /**
     * 获取员工合适的抢单
     * @param staffInfo 员工信息
     * @return
     */
    @Override
    public List<SeizeVo> getSeizeList(StaffInfo staffInfo) {
        QueryWrapper<SeizeVo> queryWrapper = new QueryWrapper<>();
        //筛选列
        queryWrapper.select("seize_id","start_time","end_time","lose_position","max_premiums","min_premiums","type","store_name"
            ,"schedule_start_time","schedule_end_time");
        //筛选状态
        queryWrapper.eq("status","未开始");
        queryWrapper.or().eq("status","抢单中");
        //按抢单开始时间升序排序,同门店优先
        queryWrapper.last("order by case when store_id = '" + staffInfo.getStoreId() +  "' then 1 else 2 end, start_time asc");
        return seizeVoMapper.selectList(queryWrapper);
    }

    /**
     * 获取抢单排行榜
     * @param date 日期 年月
     * @return
     */
    @Override
    public List<SeizeTopInfo> getSeizeTopInfos(String date) {
        return seizeVoMapper.getSeizeTopInfos(date);
    }
}




