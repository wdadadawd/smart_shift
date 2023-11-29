package com.lsu.controller.passenger;

import com.lsu.common.R;
import com.lsu.entity.PassengerFlow;
import com.lsu.service.PassengerFlowService;
import com.lsu.utils.DateUtils;
import com.lsu.utils.ExcelUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-18 20:59
 * 客流量管理
 */
@RestController
public class PassengerFlowController {
    @Resource
    private PassengerFlowService passengerFlowService;

    /**
     * 保存对应客流量预测信息
     * @param list 当天客流量集合
     * @return 添加成功信息
     */
    @RequiresRoles(value = {"shopowner","admin"},logical = Logical.OR)
    @PostMapping("/passengerFlow")
    public R<String> insertFlow(@RequestBody List<PassengerFlow> list){
        //list.forEach(System.out::println);
        passengerFlowService.insertOrUpdateFlow(list);
        return R.success("保存成功");
    }

    /**
     * 根据门店id和日期，查询当天的客流量预测信息
     * @param date  日期
     * @param storeId 门店id
     * @return 预测客流量集合
     */
    @RequiresRoles(value = {"shopowner","admin","visitor"},logical = Logical.OR)
    @GetMapping("/passengerFlow")
    public R<List<PassengerFlow>> getFlow(@DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date date,@RequestParam Integer storeId){
        List<PassengerFlow> list = passengerFlowService.getFlowByIdAndDate(date, storeId);
        return R.success(list);
    }

    /**
     * 导入excel客流量表
     * @param file
     */
    @RequiresRoles(value = {"shopowner","admin"},logical = Logical.OR)
    @PostMapping("/uploadExcel")
    public R<String> importExcel(@RequestParam("excelFile") MultipartFile file) throws IOException {
        String name=file.getOriginalFilename();
        assert name != null;
        String suffix = name.substring(name.indexOf("."));
        if(!".xls".equals(suffix)&&!".xlsx".equals(suffix)){
            return R.err("文件类型错误");
        }
        List<PassengerFlow> list = ExcelUtils.excelToShopIdList(file.getInputStream(),suffix);
        if(list == null)
            return R.err("上传错误");
//        list.forEach(System.out::println);
        passengerFlowService.insertOrUpdateFlow(list);
        return R.success("上传成功");
    }

    /**
     * 判断指定门店本周的预测客流量是否已经全部导入
     * @param startDate 周开始时间
     * @param storeId 门店id
     * @return 判断结果 true为已全部导入,false为未完整导入
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/judgeDate")
    public R<Boolean> judgeDate(@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam Date startDate,@RequestParam Integer storeId){
        if (!"星期一".equals(DateUtils.getToDay(startDate)))
            return R.err("开始时间错误" + "," + DateUtils.getToDay(startDate) + "," + DateUtils.getDate(startDate));
        List<Date> dateList = DateUtils.getWeekDay(startDate);
        Boolean aBoolean = passengerFlowService.judgeDatePassengerFlow(dateList, storeId);
        return R.success(aBoolean);
    }
}
