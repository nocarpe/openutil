package com.nocarpe.openutil.feign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignUtilClientsRegistrar.class)
public @interface EnableFeignUtilClients {

    String[] value() default {};

    /**
     * Base packages to scan for annotated components.
     * @return
     */
    String[] basePackages() default {};

}
