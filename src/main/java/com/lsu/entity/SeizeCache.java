package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author zt
 * @create 2023-07-19 15:46
 */
@Data
public class SeizeCache implements Serializable {

    /**
     * 唯一标识
     */
    private Integer seizeId;

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
     * 适配度(%)
     */
    private Double fitMeasure;

    private static final long serialVersionUID = 12213123123454L;

    public SeizeCache() {
    }

    public SeizeCache(Integer seizeId, Integer staffId, Date takeTime, Double finalPremiums) {
        this.seizeId = seizeId;
        this.staffId = staffId;
        this.takeTime = takeTime;
        this.finalPremiums = finalPremiums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeizeCache that = (SeizeCache) o;
        return Objects.equals(seizeId, that.seizeId) && Objects.equals(staffId, that.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seizeId, staffId);
    }
}
