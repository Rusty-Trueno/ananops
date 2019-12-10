package com.paascloud.provider.core.annotation;

import com.paascloud.provider.core.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * Created by rongshuai on 2019/12/9 17:41
 */
@Target({ElementType.METHOD})//表示这个自定义注解的作用对象
@Retention(RetentionPolicy.RUNTIME)//表示这个自定义注解的生命周期
@Documented//将该注解文档化
public @interface AnanLogAnnotation {
    /**
     *
     * @return
     */
    LogTypeEnum logType() default LogTypeEnum.OPERATION_LOG;

    /**
     * 是否保存请求的参数
     *
     * @return the boolean
     */
    boolean isSaveRequestData() default false;

    /**
     * 是否保存响应的结果
     *
     * @return the boolean
     */
    boolean isSaveResponseData() default false;
}
