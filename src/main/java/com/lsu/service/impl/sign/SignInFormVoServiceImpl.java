package com.lsu.service.impl.sign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.utils.DateUtils;
import com.lsu.vo.SignInFormVo;
import com.lsu.service.SignInFormVoService;
import com.lsu.mapper.sign.SignInFormVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【sign_in_form_vo】的数据库操作Service实现
* @createDate 2023-07-14 20:52:55
*/
@Service
public class SignInFormVoServiceImpl extends ServiceImpl<SignInFormVoMapper, SignInFormVo>
    implements SignInFormVoService{

    @Resource
    private SignInFormVoMapper signInFormVoMapper;

    /**
     * 搜索并分页获取签到信息
     * @param date 日期
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @param storeId 门店id
     * @return
     */
    @Override
    public Page<SignInFormVo> getPageSignInFormVo(Date date, Integer current, Integer size, String key, Integer storeId) {
        Page<SignInFormVo> page = new Page<>(current,size);
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        if (date!=null)
            queryWrapper.eq("date",date);
        if (storeId!=null)
            queryWrapper.eq("store_id",storeId);
        queryWrapper.select("sign_id","sign_time","status","way","seat","date","user_name","staff_name","store_name","store_id");
        queryWrapper.and(q -> q.like("sign_time",key)        //设置查询条件
                .or().like("status",key)
                .or().like("way",key)
                .or().like("seat",key)
                .or().like("staff_name",key)
                .or().like("user_name",key)
                .or().like("store_name",key)  //已签时间,签到状态,签到方式,签到位置,员工姓名,账号,门店名称进行模糊查询
        );
        return signInFormVoMapper.selectPage(page,queryWrapper);
    }

    /**
     * 获取指定员工今日最新的未签到记录
     * @param staffId 员工id
     * @return
     */
    @Override
    public SignInFormVo getNewSignIn(Integer staffId) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        Date date = new Date();
        queryWrapper.select("sign_id","status","store_name","start_time","end_time","store_address");
        queryWrapper.eq("date", DateUtils.getDate(date));      //筛选日期为今日
        queryWrapper.orderByAsc("start_time");                 //按开始时间排序
        queryWrapper.gt("end_time",DateUtils.getTime(date));    //结束时间大于当前时间
        queryWrapper.eq("staff_id",staffId);
        queryWrapper.eq("status","未签到");
        queryWrapper.last("LIMIT 1");          //取一条
        return signInFormVoMapper.selectOne(queryWrapper);
    }

    /**
     * 根据id获取一条签到信息
     * @param signId 签到信息id
     * @return
     */
    @Override
    public SignInFormVo getSignById(Long signId) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sign_id",signId);
        return signInFormVoMapper.selectOne(queryWrapper);
    }

    /**
     * 根据员工id获取员工的签到记录
     * @param staffId 员工id
     * @return
     */
    @Override
    public List<SignInFormVo> getSignListById(Integer staffId) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sign_id","store_name","way","seat","store_address","sign_time");
        queryWrapper.orderByDesc("date");                 //按日期降序
        queryWrapper.eq("staff_id",staffId);
        queryWrapper.and(q -> q.eq("status","已签到").or().eq("status","已迟到"));
        return signInFormVoMapper.selectList(queryWrapper);
    }


    /**
     * 获取指定员工在指定日期内的签到记录
     * @param staffId 员工id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    @Override
    public List<SignInFormVo> getSignInFormVoByDate(Integer staffId, Date startDate, Date endDate) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sign_id");
        String date1 = DateUtils.getDate(startDate);
        String date2 = DateUtils.getDate(endDate);
        String time1 = DateUtils.getTime(startDate);
        String time2 = DateUtils.getTime(endDate);
        //只要不是班次结束时间 < 请假开始时间,请假结束时间 < 班次开始时间的签到都于该请假有关
        queryWrapper.not(q -> q.gt("date",date1).or(q1 -> q1.eq("date",date1).le("end_time",time1))
                .or().lt("date",date2).or(q1 -> q1.eq("date",date2).ge("start_time",time2)));
        queryWrapper.eq("status","未签到");
        queryWrapper.eq("staff_id",staffId);
        return signInFormVoMapper.selectList(queryWrapper);
    }

    /**
     * 统计今日到目前迟到班次
     * @param storeId 门店id
     * @return
     */
    @Override
    public Integer getNowLateCount(Integer storeId) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        Date date = new Date();
        //开始时间 >= 当前时间-5
        queryWrapper.gt("start_time",DateUtils.getOutSecondsTime(DateUtils.getTime(date),-5));
        queryWrapper.eq("status","已迟到");
        queryWrapper.eq("date",DateUtils.getDate(date));
        return Math.toIntExact(signInFormVoMapper.selectCount(queryWrapper));
    }

    /**
     * 统计今日到目前缺勤班次
     * @param storeId 门店id
     * @return
     */
    @Override
    public Integer getNowDeficiencyCount(Integer storeId) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        Date date = new Date();
        //结束时间 <= 当前时间
        queryWrapper.le("end_time",DateUtils.getTime(date));
        queryWrapper.eq("status","未签到");
        queryWrapper.eq("date",DateUtils.getDate(date));
        return Math.toIntExact(signInFormVoMapper.selectCount(queryWrapper));
    }

    /**
     * 统计今日到目前请假班次
     * @param storeId 门店id
     * @return
     */
    @Override
    public Integer getNowLeaveCount(Integer storeId) {
        QueryWrapper<SignInFormVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        Date date = new Date();
        //开始时间 <= 当前时间
        queryWrapper.le("start_time",DateUtils.getTime(date));
        queryWrapper.eq("status","已请假");
        queryWrapper.eq("date",DateUtils.getDate(date));
        return Math.toIntExact(signInFormVoMapper.selectCount(queryWrapper));
    }


}




