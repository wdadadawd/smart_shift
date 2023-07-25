package com.lsu;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.entity.PassengerFlow;
import com.lsu.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SmartShiftApplicationTests {

    @Resource
    private RuleMapService ruleMapService;

    @Resource
    private StoreRuleService storeRuleService;

    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private PassengerFlowService passengerFlowService;

    @Resource
    private SignInFormVoService signInFormVoService;

    @Resource
    private LeaveVoService leaveVoService;


    @Test
    void contextLoads() {

    }

    @Test
    public void test(){
        staffInfoService.getStaffListByStore(1).forEach(System.out::println);
    }

    @Test
    public void test2(){
        String dateString = "2023-07-16";
        String format = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
