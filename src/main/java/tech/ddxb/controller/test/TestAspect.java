package tech.ddxb.controller.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by leahhuang on 2018/7/24.
 */
@Aspect
@Component
public class TestAspect {
    @Pointcut("@annotation(tech.ddxb.controller.test.AuthOpt)")
    public void cut() {
    }

    @Around("cut()")
    public void advice(JoinPoint joinPoint) {
        System.out.println("ok。。。。");
    }
}
