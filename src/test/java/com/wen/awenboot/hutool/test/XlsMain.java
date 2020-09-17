package com.wen.awenboot.hutool.test;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.wen.awenboot.hutool.test.bean.UserTest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 19:16
 */
@Slf4j
public class XlsMain {


    public static void main(String[] args) {
        test1();

    }

    private static void test1() {
        List<UserTest> list = new ArrayList<>();
        // 使用此功能需要导入poi包
        RowHandler handler = new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {
                String r1 = rowList.get(0).toString();
                UserTest ut = new UserTest();
                ut.setName(r1);
                list.add(ut);
                log.info("[{}] [{}] {}", sheetIndex, rowIndex, rowList);
            }
        };

        ExcelUtil.readBySax("D:\\test-data\\xls-test.xls", 0, handler);
        log.info("list=" + list.toString());
    }

    private static void testObj() {
        CsvReader reader = CsvUtil.getReader();
        // 此方法必须包含标题
        final List<UserTest> list = reader.read(
                ResourceUtil.getUtf8Reader("D:\\test-data\\csv-test.csv"), UserTest.class);

        list.stream().forEach(user -> {
            log.info(user.toString());
        });

    }
}
