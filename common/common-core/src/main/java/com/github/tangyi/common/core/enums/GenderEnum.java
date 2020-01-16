package com.github.tangyi.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 18:53
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    MEN("男", 0), WOMEN("女", 1);

    private String name;

    private Integer value;

    public static GenderEnum matchByValue(Integer value) {
        for (GenderEnum item : GenderEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return MEN;
    }

    public static GenderEnum matchByName(String name) {
        for (GenderEnum item : GenderEnum.values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        return MEN;
    }

}
