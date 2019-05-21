package com.cqut.icode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 谭强
 * @date 2019/5/11
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FieldType {
    // field的包装类
    // 被注解修饰的field默认认为其name同数据库表中的列名相同
    String value() default "";
}
