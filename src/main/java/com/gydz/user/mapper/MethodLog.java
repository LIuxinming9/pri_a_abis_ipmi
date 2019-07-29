package com.gydz.user.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MethodLog {

	/**
     * 记录操作描述
     *
     * @return
     */
    String remark() default "";
 
    /**
     * 增:0,删:1,改:2,查:3(默认为查)
     *
     * @return
     */
    String openType() default "3";

}
