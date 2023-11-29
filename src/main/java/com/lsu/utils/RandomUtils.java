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
    public static int getRandom(List<Integer> list,int maxWeekTime){
        List<Integer> listClone = new ArrayList<>();
        int sum = 0;                       //总时间
        for (int i=0;i<list.size();i++){
            int surTime = maxWeekTime-list.get(i);
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

    public static String getUUID(Integer length){
        // 定义UUID允许的字符
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // 创建随机数生成器
        Random random = new Random();

        // 用于存储生成的UUID的StringBuilder
        StringBuilder uuidBuilder = new StringBuilder(length);

        // 通过从允许的字符集合中随机选择字符来生成UUID
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            uuidBuilder.append(randomChar);
        }

        // 将生成的UUID作为字符串返回
        return uuidBuilder.toString();
    }
}
