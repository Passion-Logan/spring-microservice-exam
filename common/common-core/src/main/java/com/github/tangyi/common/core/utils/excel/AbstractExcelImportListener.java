package com.github.tangyi.common.core.utils.excel;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * 封装简单数据导入的逻辑，解析3000条刷一次数据库
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 12:38
 */
public abstract class AbstractExcelImportListener<T> extends AnalysisEventListener<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每隔3000条存储数据库
     */
    private static final int BATCH_COUNT = 3000;

    /**
     * 需要导入的数据
     */
    private List<T> dataList = new ArrayList<>();

    @Override
    public void invoke(T dataModel, AnalysisContext context) {
        dataList.add(dataModel);
        // 达到达到BATCH_COUNT则保存进数据库，防止几万条数据在内存中，容易OOM
        if (dataList.size() >= BATCH_COUNT) {
            saveData(dataList);
            // 存储完成清理list
            dataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 最后一次保存
        saveData(dataList);
        logger.info("All data is parsed!");
    }

    /**
     * 保存数据，子类实现
     *
     * @param dataList
     */
    public abstract void saveData(List<T> dataList);
}
