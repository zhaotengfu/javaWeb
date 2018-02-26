package com.ztf.core.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    public String value();

    public enum FontColor {
        BLUE,
        RED,
        GREEN
    };
    String name() default "";

    FontColor fontColor() default FontColor.RED;
}