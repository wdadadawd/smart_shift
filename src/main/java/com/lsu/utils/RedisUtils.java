package com.lsu.utils;


import com.lsu.entity.SeizeCache;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加一个抢单信息到对应的抢单集合中
     * @param seizeCache 抢单信息
     */
    public void addSeizeCacheAndTime(SeizeCache seizeCache){
        String prefixName = "seizeList";
        String finalName = prefixName + seizeCache.getSeizeId();
        BoundSetOperations<String, Object> setOps = redisTemplate.boundSetOps(finalName);
        setOps.add(seizeCache);
    }

    /**
     * 判断该人是否已过抢该单
     * @param seizeCache 抢单信息
     * @return
     */
    public Boolean judgeSeizeCache(SeizeCache seizeCache){
        List<SeizeCache> seizeCacheList = getSeizeCacheList(seizeCache.getSeizeId());
        if (seizeCacheList == null)
            return false;
//        System.out.println(1231);
//        seizeCacheList.forEach(System.out::println);
        return seizeCacheList.contains(seizeCache);         //判断该人是否已过抢该单
    }

    /**
     * 获取指定key的SeizeCache集合
     * @param seizeId 抢单id
     * @return
     */
    public List<SeizeCache> getSeizeCacheList(Integer seizeId){
        String prefixName = "seizeList";
        String finalName = prefixName + seizeId;
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        Set<Object> set = setOperations.members(finalName);
        if (set == null)
            return null;
        List<SeizeCache> seizeList = new ArrayList<>();
        for (Object obj : set) {
            seizeList.add((SeizeCache) obj);
        }
        return seizeList;
    }


    /**
     * 删除指定的抢单集合
     * @param seizeId 抢单id
     */
    public void deleteSeizeCacheList(Integer seizeId){
        String prefixName = "seizeList";
        String finalName = prefixName + seizeId;
        redisTemplate.delete(finalName);
    }

    /**
     * 获取该邮箱的发送次数
     * @param email 邮箱
     */
    public Integer getMailCodeCount(String email,String use){
        return (Integer) redisTemplate.opsForValue().get(email + "_" + use+ "_count");
    }

    /**
     * 保存该邮箱的验证码
     * @param email 邮箱
     * @param code 验证码
     */
    public void saveMailCode(String email,String code,String use){
        //将邮箱验证码以邮件地址为key存入redis,5分钟过期
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email + "_" + use+ "_code", code, Duration.ofMinutes(5));
        Integer mailCodeCount = getMailCodeCount(email,use);
        if (mailCodeCount == null){
            valueOperations.set(email + "_" + use+ "_count", 1, Duration.ofDays(1));
        }else{
            Long expire = redisTemplate.getExpire(email + "_" + use + "_count");
//            System.out.println(mailCodeCount);
//            System.out.println(expire);
            valueOperations.set(email + "_" + use+ "_count", mailCodeCount+1,expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取邮箱的验证码
     * @param email 邮箱
     */
    public String getMailCode(String email,String use){
        return (String) redisTemplate.opsForValue().get(email + "_" + use+ "_code");
    }

}

