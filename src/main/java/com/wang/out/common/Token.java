package com.wang.out.common;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
// 表示该注解是用在方法上的(方法注解)
@Retention(RetentionPolicy.RUNTIME)
// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented
//说明该注解将被包含在javadoc中
@Inherited
public @interface Token {

}
