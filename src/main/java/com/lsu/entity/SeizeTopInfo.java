package com.lsu.entity;

import lombok.Data;

/**
 * @author zt
 * @create 2023-07-21 16:24
 */
@Data
public class SeizeTopInfo {

    /**
     * 员工名称
     */
    private String staffName;

    /**
     * 员工当月抢单奖励总和
     */
    private Double premiums;

    /**
     * 员工当月抢单排名
     */
    private Integer ranking;

    /**
     * 排名奖励
     */
    private Double rankPremiums;

    /**
     * 员工当月抢单次数
     */
    private Integer seizeCount;

}
