package com.my.im.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface SessionCheck {

        String value() default "true";//默认为空，因为名字是value，实际操作中可以不写"value="
}
