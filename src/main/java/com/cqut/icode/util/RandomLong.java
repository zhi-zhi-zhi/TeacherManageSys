package com.cqut.icode.util;

/**
 * 用户注册时生成11位数字作为username
 * @author 谭强
 * @date 2019/5/16
 */
public class RandomLong {
    public static Long create() {
        return Long.parseLong((System.currentTimeMillis() + "").substring(2));
    }
}
