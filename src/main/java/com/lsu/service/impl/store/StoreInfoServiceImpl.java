package com.lsu.service.impl.store;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.RuleMap;
import com.lsu.entity.StoreInfo;
import com.lsu.entity.StoreRule;
import com.lsu.mapper.store.RuleMapMapper;
import com.lsu.mapper.staff.StaffInfoMapper;
import com.lsu.mapper.store.StoreInfoMapper;
import com.lsu.mapper.store.StoreRuleMapper;
import com.lsu.service.StoreInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【store_info】的数据库操作Service实现
* @createDate 2023-03-12 14:59:29
*/
@Service
@Transactional
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfo>
    implements StoreInfoService{

    @Resource
    private StoreInfoMapper storeInfoMapper;          //门店信息mapper

    @Resource
    private RuleMapMapper ruleMapMapper;                 //默认规则mapper

    @Resource
    private StoreRuleMapper storeRuleMapper;            //门店规则mapper

    @Resource
    private StaffInfoMapper staffInfoMapper;            //员工信息mapper

    /**
     * 获取全部门店信息(不包含门店规则)
     * @return  门店信息集合
     */
    @Override
    public List<StoreInfo> getAllStoreInfo() {
        List<StoreInfo> list = storeInfoMapper.selectList(null);
        return list;
    }

    /**
     * 删除门店
     * @param id 门店id
     */
    @Override
    public Integer deleteStoreInfo(Integer id) {
        QueryWrapper<StoreRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",id);
        storeRuleMapper.delete(queryWrapper);         //删除规则
        staffInfoMapper.updateStoreId(id);            //更新原属门店员工的所属门店id为NULL
        return storeInfoMapper.deleteById(id);
    }

    /**
     * 添加门店信息
     * @param storeInfo 门店信息
     */
    @Override
    public Integer insertStoreInfo(StoreInfo storeInfo,Integer userId) {
        int result = storeInfoMapper.insert(storeInfo);
        List<RuleMap> ruleMapList = ruleMapMapper.selectList(null);
        StoreRule storeRule = new StoreRule();                //创建一条规则
        storeRule.setStoreId(storeInfo.getId());              //设置规则所属门店id
        storeRule.setStatus(false);                           //设置规则状态
        storeRule.setUserId(userId);                          //设置更新人id
        storeRule.setUpdateTime(new Date());                 //设置更新时间
        for(RuleMap r: ruleMapList){
            storeRule.setType(r.getRuleType());         //设置规则类型
            storeRuleMapper.insert(storeRule);          //添加规则
        }
        return result;
    }

    /**
     * 修改门店信息
     * @param storeInfo 门店信息
     */
    @Override
    public Integer updateStoreInfo(StoreInfo storeInfo) {
        return storeInfoMapper.updateById(storeInfo);
    }

    /**
     * 获取全部门店的id和名称
     */
    @Override
    public List<StoreInfo> getALLStoreName() {
        QueryWrapper<StoreInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        return storeInfoMapper.selectList(queryWrapper);
    }

    /**
     * 根据门店id获取指定门店信息
     * @return
     */
    @Override
    public StoreInfo getOneStore(Integer storeId) {
        QueryWrapper<StoreInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",storeId);
        return storeInfoMapper.selectOne(queryWrapper);
    }
}




