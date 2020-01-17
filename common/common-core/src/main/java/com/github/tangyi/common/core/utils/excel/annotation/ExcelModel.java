package com.github.tangyi.common.core.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 *
 * @Target 注解的作用目标
 * ElementType.TYPE   //接口、类、枚举、注解
 * ElementType.FIELD //字段、枚举的常量
 * ElementType.METHOD //方法
 * ElementType.PARAMETER //方法参数
 * ElementType.CONSTRUCTOR  //构造函数
 * ElementType.LOCAL_VARIABLE  //局部变量
 * ElementType.ANNOTATION_TYPE  //注解
 * ElementType.PACKAGE  //包
 *
 * @Retention 用来修饰注解，是注解的注解，称为元注解
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 * 这3个生命周期分别对应于：Java源文件(.java文件) ---> .class文件 ---> 内存中的字节码。
 * 一般如果需要在运行时去动态获取注解信息，那只能用 RUNTIME 注解，比如@Deprecated使用RUNTIME注解
 * 如果要在编译时进行一些预处理操作，比如生成一些辅助代码（如 ButterKnife），就用 CLASS注解；
 * 如果只是做一些检查性的操作，比如 @Override 和 @SuppressWarnings，使用SOURCE 注解。
 *
 * 注解@Override用在方法上，当我们想重写一个方法时，在方法上加@Override，当我们方法的名字出错时，编译器就会报错
 * 注解@Deprecated，用来表示某个类或属性或方法已经过时，不想别人再用时，在属性和方法上用@Deprecated修饰
 * 注解@SuppressWarnings用来压制程序中出来的警告，比如在没有用泛型或是方法已经过时的时候
 *
 * @Inherited 注解标记其他的注解用于指明标记的注解是可以被自动继承的。
 * 注意：此注解只对注解标记的超类有效，对接口是无效的。
 * 类继承关系中@Inherited的作用
 *
 * 类继承关系中，子类会继承父类使用的注解中被@Inherited修饰的注解
 *
 * 接口继承关系中@Inherited的作用
 * 接口继承关系中，子接口不会继承父接口中的任何注解，不管父接口中使用的注解有没有被@Inherited修饰
 *
 * 类实现接口关系中@Inherited的作用
 * 类实现接口时不会继承任何接口中定义的注解
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelModel {

    String value() default "";

    String[] sheets() default {"sheet1"};
}
