package com.cqut.icode.factory;

/**
 * 用户注册时生成11位数字作为username
 * @author 谭强
 * @date 2019/5/16
 */
public class CreateId {
    public static Long createUsername() {
        return Long.parseLong((System.currentTimeMillis() + "").substring(2));
    }
}
