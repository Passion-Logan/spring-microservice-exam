package com.github.tangyi.common.core.vo;

import com.github.tangyi.common.core.model.Log;
import com.github.tangyi.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * 日志Vo
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 16:22
 */
@Data
public class LogVo extends BaseEntity<LogVo> {

    private Log log;

    private String username;
}
