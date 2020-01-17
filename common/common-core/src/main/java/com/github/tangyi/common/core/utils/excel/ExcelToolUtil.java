package com.github.tangyi.common.core.utils.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.tangyi.common.core.utils.DateUtils;
import com.github.tangyi.common.core.utils.Servlets;
import com.github.tangyi.common.core.utils.excel.annotation.ExcelModel;
import com.github.tangyi.common.core.utils.excel.exception.ExcelException;

import lombok.extern.slf4j.Slf4j;

/**
 * excel导入导出工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:40
 */
@Slf4j
public class ExcelToolUtil {

    private static final String DEFAULT_SHEET_NAME = "sheet1";

    /**
     * 私有构造方法，禁止实例化
     */
    private ExcelToolUtil() {}

    /**
     * 导出Excel
     *
     * @param request
     * @param response
     * @param dataList
     * @param clazz
     * @param <T>
     */
    public static <T> void writeExcel(HttpServletRequest request, HttpServletResponse response, List<T> dataList,
        Class<T> clazz) {
        // 获取fileName和sheetName
        ExcelModel excelModel = clazz.getDeclaredAnnotation(ExcelModel.class);
        String fileName = DateUtils.localDateMillisToString(LocalDateTime.now());
        String sheetName = DEFAULT_SHEET_NAME;
        if (excelModel != null) {
            fileName = excelModel.value() + fileName;
            sheetName = excelModel.sheets()[0];
        }
        // 导出
        writeExcel(request, response, dataList, fileName, sheetName, clazz);
    }

    /**
     * 导出Excel
     *
     * @param request
     * @param response
     * @param dataList
     * @param fileName
     * @param sheetName
     * @param clazz
     * @param <T>
     */
    public static <T> void writeExcel(HttpServletRequest request, HttpServletResponse response, List<T> dataList,
        String fileName, String sheetName, Class<T> clazz) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter =
                EasyExcelFactory.write(getOutputStream(fileName, request, response, ExcelTypeEnum.XLSX), clazz).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetName).build();
            excelWriter.write(dataList, writeSheet);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 获取OutputStream
     *
     * @param fileName
     * @param request
     * @param response
     * @param excelTypeEnum
     * @return
     */
    private static OutputStream getOutputStream(String fileName, HttpServletRequest request,
        HttpServletResponse response, ExcelTypeEnum excelTypeEnum) {
        try {
            // 设置响应头，处理浏览器间的中文乱码问题
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
                Servlets.getDownName(request, fileName + excelTypeEnum.getValue()));
            return response.getOutputStream();
        } catch (Exception e) {
            throw new ExcelException("get OutputStream error!");
        }
    }

    /**
     * 导入Excel
     *
     * @param inputStream
     * @param clazz
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Boolean roadExcel(InputStream inputStream, Class<T> clazz, AnalysisEventListener listener) {
        Boolean success = Boolean.TRUE;
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcelFactory.read(inputStream, clazz, listener).build();
            ReadSheet readSheet = EasyExcelFactory.readSheet(0).build();
            excelReader.read(readSheet);
        } catch (Exception e) {
            log.error("Read excel error: {}", e.getMessage(), e);
            success = Boolean.FALSE;
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return success;
    }
}
