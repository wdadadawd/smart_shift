package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName seize_orders
 */
@TableName(value ="seize_orders")
@Data
public class SeizeOrders implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer seizeId;

    /**
     * 抢单所属门店ID
     */
    private Integer storeId;

    /**
     * 抢单开始时间
     */
    private Date startTime;

    /**
     * 抢单结束时间
     */
    private Date endTime;

    /**
     * 抢单状态
     */
    private String status;

    /**
     * 缺失岗位
     */
    private String losePosition;

    /**
     * 对应班次id
     */
    private Long scheduleId;

    /**
     * 班次最大额外奖励
     */
    private Double maxPremiums;

    /**
     * 班次最少额外奖励
     */
    private Double minPremiums;

    /**
     * 抢单类型,rapid先到先得,superior则优
     */
    private String type;

    /**
     * 抢单人员ID
     */
    private Integer staffId;

    /**
     * 抢得时间
     */
    private Date takeTime;

    /**
     * 最终奖励金额
     */
    private Double finalPremiums;

    /**
     * 是否完成
     */
    private Boolean isAccomplish;

    public SeizeOrders() {

    }

    public SeizeOrders(Integer storeId, String status, String losePosition, Long scheduleId) {
        this.storeId = storeId;
        this.status = status;
        this.losePosition = losePosition;
        this.scheduleId = scheduleId;
    }

    public SeizeOrders(Integer seizeId, String status, Integer staffId, Date takeTime, Double finalPremiums, Boolean isAccomplish) {
        this.seizeId = seizeId;
        this.status = status;
        this.staffId = staffId;
        this.takeTime = takeTime;
        this.finalPremiums = finalPremiums;
        this.isAccomplish = isAccomplish;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}