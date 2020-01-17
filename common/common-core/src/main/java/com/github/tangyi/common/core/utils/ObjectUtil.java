package com.github.tangyi.common.core.utils;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:20
 */
public class ObjectUtil {

    /**
     * 将字符串转换为double,如果字符串为空或者null，则自动转换为0.0。
     *
     * @param toConvert 需要转换的字符串
     * @return double
     */
    public static double obj2Double(Object toConvert) {
        if ((toConvert != null) && ((toConvert instanceof Double))) {
            return ((Double) toConvert).doubleValue();
        }
        double d = 0.0D;
        try {
            d = Double.parseDouble(String.valueOf(toConvert));
        } catch (Exception ex) {
        }
        return d;
    }

}
