package com.github.tangyi.common.core.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
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
