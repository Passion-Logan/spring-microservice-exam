package com.github.tangyi.common.core.vo;

import com.github.tangyi.common.core.persistence.BaseEntity;

import lombok.Data;

/**
 * 部门vo
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 16:21
 */
@Data
public class DeptVo extends BaseEntity<DeptVo> {

    private String deptName;

}
