package com.wen.awenboot.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wen
 * @version 1.0
 * @date 2021/4/20 11:04
 */
@Slf4j
public class TestPhone {

    public static final String D_PHONE_TXT = "D://data/dna-module/push/";
    public static final String SW = D_PHONE_TXT + "sw.txt";
    public static final String BW = D_PHONE_TXT + "bw.txt";
    public static final String QW = D_PHONE_TXT + "qw.txt";
    public static final String WW = D_PHONE_TXT + "ww.txt";
    public static final String phone = "10102000000";

    public static long lineCount(File file) {
        try {
            return Files.lines(Paths.get(file.getPath())).count();
        } catch (Exception e) {
            System.out.println("统计文件行数异常,file={}" + file.getPath() + e.getMessage());
            return 0;
        }
    }

    public static void make() throws IOException {
        makeFile(SW, 500000);
        makeFile(BW, 5000000);
        makeFile(QW, 50000000);
        makeFile(WW, 200000000);
    }

    public static void main(String[] args) throws IOException {
//        make();
        test(SW, 10000250000L);
        test(BW, 10002500000L);
        test(QW, 10025000000L);
        test(WW, 10100000000L);

    }

    private static void test(String path, long m) throws IOException {
        File file = new File(path);
        log.info("lineCount={},文件大小={}", lineCount(file), file.length());
        log.info("中段出现耗时如下:{}", m);
        for (int i = 0; i < 10; i++) {
            readTxt3(file, m + "");
        }

        log.info("不存在遍历整个文件");
        for (int i = 0; i < 10; i++) {
            readTxt3(file, m + "11111111111");
        }
        log.info("end");
        log.info("====================================");
        log.info("====================================");
        log.info("====================================");
    }

    private static String getTest() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000 / 1000 + "MB";
    }


    /**
     * @throws IOException
     * @Title: readTxt3
     * @Description: 使用org.apache.commons.io.FileUtils，apache工具类解析文件
     */
    public static void readTxt3(File file, String phone) throws IOException {
        long start = System.currentTimeMillis();

        LineIterator it = FileUtils.lineIterator(file, "UTF-8");

        while (it.hasNext()) {
            if (phone.equals(it.next())) {
//                log.info("finded,{}", phone);
                break;
            }
        }

        it.close();

        long end = System.currentTimeMillis();
        log.info("使用内存=" + getTest() + ",毫秒=" + (end - start) + "ms");
    }

    //    @Test
    //造数据，测试下面各个方法读取数据性能
    public static void makeFile(String path, int length) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        long couter = 10000000000L;
        //2百万
        for (int i = 0; i < length; i++) {
            bw.write(Long.toString(++couter));
            bw.newLine();
        }

        bw.close();
        os.close();
    }


}
