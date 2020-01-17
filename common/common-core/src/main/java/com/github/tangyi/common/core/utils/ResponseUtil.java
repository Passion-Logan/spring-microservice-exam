package com.github.tangyi.common.core.utils;

import com.github.tangyi.common.core.constant.ApiMsg;
import com.github.tangyi.common.core.model.ResponseBean;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:35
 */
public class ResponseUtil {

    private ResponseUtil() {}

    /**
     * 是否成功
     *
     * @param responseBean
     * @return
     */
    public static boolean isSuccess(ResponseBean<?> responseBean) {
        return responseBean != null && responseBean.getCode() == ApiMsg.KEY_SUCCESS;
    }
}
