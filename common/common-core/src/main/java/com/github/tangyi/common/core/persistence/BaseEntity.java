package com.github.tangyi.common.core.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.tangyi.common.core.constant.CommonConstant;
import com.github.tangyi.common.core.utils.DateUtils;
import com.github.tangyi.common.core.utils.IdGen;
import com.github.tangyi.common.core.utils.SysUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entity基类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:12
 */
@Data
@NoArgsConstructor
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Long id;

    /**
     * 创建者
     */
    protected String creator;

    /**
     * 创建日期
     */
    protected Date createDate;

    /**
     * 更新者
     */
    protected String modifier;

    /**
     * 更新日期
     */
    protected Date modifyDate;

    /**
     * 删除标记 0:正常，1-删除
     */
    protected Integer delFlag = CommonConstant.DEL_FLAG_NORMAL;

    /**
     * 系统编号
     */
    protected String applicationCode;

    /**
     * 租户编号
     */
    protected String tenantCode;

    /**
     * 是否为新记录
     */
    protected boolean isNewRecord;

    public BaseEntity(Long id) {
        this();
        this.id = id;
    }

    /**
     * 是否为新记录
     *
     * @return boolean
     */
    public boolean isNewRecord() {
        return this.isNewRecord || this.getId() == null;
    }

    /**
     * 设置基本属性
     *
     * @param userCode        用户编码
     * @param applicationCode 系统编号
     */
    public void setCommonValue(String userCode, String applicationCode) {
        setCommonValue(userCode, applicationCode, SysUtil.getTenantCode());
    }

    /**
     * 设置基本属性
     *
     * @param userCode        用户编码
     * @param applicationCode 系统编号
     * @param tenantCode      租户编号
     */
    public void setCommonValue(String userCode, String applicationCode, String tenantCode) {
        Date currentDate = DateUtils.asDate(LocalDateTime.now());
        if (this.isNewRecord()) {
            this.setId(IdGen.snowflakeId());
            this.setNewRecord(true);
            this.creator = userCode;
            this.createDate = currentDate;
        }
        this.modifier = userCode;
        this.modifyDate = currentDate;
        this.delFlag = 0;
        this.applicationCode = applicationCode;
        this.tenantCode = tenantCode;
    }

}
