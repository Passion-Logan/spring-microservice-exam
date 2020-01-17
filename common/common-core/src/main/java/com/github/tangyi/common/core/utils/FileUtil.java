package com.github.tangyi.common.core.utils;

/**
 * 文件工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:13
 */
public class FileUtil {

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileNameEx(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                return fileName.substring(dot + 1, fileName.length());
            }
        }
        return "";
    }
}
