package com.cqut.icode.entities.base;

import java.io.Serializable;

/**
 * @author 谭强
 * @date 2018/8/19
 */
public abstract class BaseEntity implements Serializable {
    /**
     * serialVersionUID 就是控制版本是否兼容的
     * 实际上这里并没有用到文件读写，所以实现序列化与否没啥意义
     * */
    private static final long serialVersionUID = 1L;

    /**
     * 每个实体类都应该实现这个方法从而知道该类所对应的表名
     * @return 返回表名
     */
    public  abstract  String getTableName();
}
