package com.lsu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zt
 * @create 2023-07-13 13:52
 */
public class RandomUtils {

    //轮盘法获取随机数
    public static int getRandom(List<Integer> list){
        List<Integer> listClone = new ArrayList<>();
        int sum = 0;                       //总时间
        for (int i=0;i<list.size();i++){
            int surTime = 40-list.get(i);
            listClone.add(i,surTime);
            sum+=surTime;
        }
        if (sum==0)                //无人可安排
            return 0;
        Random random = new Random();
        int randomNumber = random.nextInt(sum) + 1;
        int x = 0;
        for (int i=0;i<listClone.size();i++){
            x+=listClone.get(i);
            if (x >= randomNumber && x!=0)
                return  i;
        }
        return -1;
    }
}
