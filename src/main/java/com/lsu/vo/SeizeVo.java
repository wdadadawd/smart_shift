package com.lsu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName seize_vo
 */
@TableName(value ="seize_vo")
@Data
public class SeizeVo implements Serializable {
    /**
     * 唯一标识
     */
    @TableId
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
    private Integer isAccomplish;

    /**
     * 
     */
    private Date scheduleStartTime;

    /**
     * 
     */
    private Date scheduleEndTime;

    /**
     * 
     */
    private String status;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 姓名
     */
    private String staffName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}