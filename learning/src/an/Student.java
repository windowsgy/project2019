package an;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jlgaoyuan on 2018/11/14.
 *
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})//应用范围 ，方法或类
@Retention(value = RetentionPolicy.RUNTIME)//被反射读取,SOURCE 不可以被反射读取
public @interface Student {
    String studentName() default "";
    int age() default 0;
    String [] schools() default {"a","b"};
}
