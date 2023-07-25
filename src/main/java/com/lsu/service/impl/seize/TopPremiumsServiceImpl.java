package com.lsu.service.impl.seize;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.TopPremiums;
import com.lsu.service.TopPremiumsService;
import com.lsu.mapper.seize.TopPremiumsMapper;
import com.lsu.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【top_premiums】的数据库操作Service实现
* @createDate 2023-07-21 20:37:39
*/
@Service
public class TopPremiumsServiceImpl extends ServiceImpl<TopPremiumsMapper, TopPremiums>
    implements TopPremiumsService{

    @Resource
    private TopPremiumsMapper topPremiumsMapper;

    /**
     * 获取某月排行奖励
     * @param date 日期
     * @return
     */
    @Override
    public List<TopPremiums> getTopPremiumsListByDate(Date date) {
        String yearAndMonth = DateUtils.getYearAndMonth(date);
        QueryWrapper<TopPremiums> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("premiums_date",yearAndMonth);
        return topPremiumsMapper.selectList(queryWrapper);
    }

    /**
     * 删除某月的排行奖励
     * @param date 日期
     */
    @Override
    public void deleteTopPremiumsListByDate(Date date) {
        String yearAndMonth = DateUtils.getYearAndMonth(date);
        QueryWrapper<TopPremiums> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("premiums_date",yearAndMonth);
        topPremiumsMapper.delete(queryWrapper);
    }

    /**
     * 保存某月的排行奖励的集合
     * @param list 排行奖励集合
     * @param date 日期
     */
    @Override
    public void insertTopPremiumsList(List<TopPremiums> list, Date date) {
        for (TopPremiums tp: list){
            tp.setPremiumsDate(date);
            topPremiumsMapper.insert(tp);
        }
    }
}




