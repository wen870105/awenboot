package com.wen.awenboot.hutool.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.wen.awenboot.hutool.test.bean.UserTest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/9 19:16
 */
@Slf4j
public class CSVMain {

    public static void main(String[] args) {
        test1();

        testObj();
    }

    private static void test1() {
        CsvReader reader = CsvUtil.getReader();
        CsvData data = reader.read(FileUtil.file("D:\\test-data\\csv-test.csv"));
        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            log.info(csvRow.getRawList().toString());
        }

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
