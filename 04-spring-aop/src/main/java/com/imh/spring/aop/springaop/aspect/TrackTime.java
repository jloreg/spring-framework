package com.imh.spring.aop.springaop.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)			//Methods: I would want to use TrackTime only on methods, I don't want to use it on classes.
@Retention(RetentionPolicy.RUNTIME)	//Runtime: I would want it to be available at run time even when the program is executing.
public @interface TrackTime {

}