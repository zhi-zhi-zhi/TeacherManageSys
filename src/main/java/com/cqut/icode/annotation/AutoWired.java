package com.cqut.icode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 谭强
 * @date 2019/5/21
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AutoWired {
}
