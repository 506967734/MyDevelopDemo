package com.demo.common.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewAttrs {
    /**
     * View id
     *
     * @return
     */
    int id() default -1;

    /**
     * 要设置的Listener集合，即{@link #listenerName()}集合，this表示当前所在类即为listener。
     * <p/>
     * <b>注意，使用this只能设置一个listener。
     *
     * @return
     */
    String[] listeners() default {};

    /**
     * Listener名，用以标注Listener。为了防止混淆后变量名被改变，目前使用注解来进行标注。
     *
     * @return
     */
    String listenerName() default "";

    /**
     * 若设置了此项并且相对应的View为TextView或其子类， 则会尝试着从传入的对象中找寻相关变量并且将变量值赋予View。
     * 格式说明:
     * <br>-x，表明在传入的对象中查找名为x的变量。
     * <br>-x.y，表明在传入的对象中查找x变量，然后在在x变量中查找y变量，以此类推.(支持x.y.m....)
     * <br>-x@stringIdName，将会先从string中获取id名为stringIdName的字符串，然后再同x变量进行格式化操作，即调用{@link String#format(String, Object...)}
     * <p/>
     * <p/>
     * <b>注意：若传入的数据对象中相应的变量实现了JavaBean的get方法，则会优先调用get方法(get方法名称不区分大小写)，否则会直接获取变量（此时变量必须为public修饰）
     *
     * @return
     */
    String variable() default "";
}
