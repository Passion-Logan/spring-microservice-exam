package com.github.tangyi.common.core.vo;

import com.github.tangyi.common.core.persistence.BaseEntity;

import lombok.Data;

/**
 * 附件VO
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 16:21
 */
@Data
public class AttachmentVo extends BaseEntity<AttachmentVo> {

    /**
     * 附件名称
     */
    private String attachName;

    /**
     * 附件大小
     */
    private String attachSize;

    /**
     * 业务流水号
     */
    private String busiId;

    /**
     * 业务类型
     */
    private String busiType;

    /**
     * 业务模块
     */
    private String busiModule;

}
