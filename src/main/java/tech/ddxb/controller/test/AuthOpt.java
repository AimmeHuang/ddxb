package tech.ddxb.controller.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by leahhuang on 2018/7/24.
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RUNTIME)
public @interface AuthOpt {
    AuthType[] value();
}
