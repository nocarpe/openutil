package com.nocarpe.openutil.feign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FeignUtilClient {


    String value() default "";


    String name() default "";



    String url() default "";
}
