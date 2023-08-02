package com.lsu.utils;

import java.util.Random;

/**
 * @author zt
 * @create 2023-07-27 15:00
 */
public class CodeUtils {

    // 生成指定长度的随机验证码
    public static String generateVerificationCode(int length) {
        // 定义验证码字符源
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();

        // 使用StringBuilder构建验证码
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();

        // 随机生成指定长度的验证码
        for (int i = 0; i < length; i++) {
            // 从字符源中随机选择一个字符并添加到验证码中
            int index = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(index));
        }

        // 返回生成的验证码
        return codeBuilder.toString();
    }
}
