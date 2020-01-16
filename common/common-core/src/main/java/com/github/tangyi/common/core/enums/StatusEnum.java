package com.github.tangyi.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 18:56
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    ENABLE("启用", 0), DISABLE("禁用", 1);

    private String name;

    private Integer value;

    public static StatusEnum matchByValue(Integer value) {
        for (StatusEnum item : StatusEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return ENABLE;
    }

    public static StatusEnum matchByName(String name) {
        for (StatusEnum item : StatusEnum.values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        return ENABLE;
    }

}
