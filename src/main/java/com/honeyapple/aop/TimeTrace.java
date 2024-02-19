package com.honeyapple.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 어노테이션 적용 위치
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 영향미치는 시기
public @interface TimeTrace {

}
