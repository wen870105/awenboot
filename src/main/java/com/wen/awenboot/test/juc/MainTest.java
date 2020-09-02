package com.wen.awenboot.test.juc;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wen
 * @version 1.0
 * @date 2020/6/23 19:44
 */
public class MainTest {
    private static Logger logger = LoggerFactory.getLogger(MainTest.class);
    private static String path = "D:\\test123.txt";



    public static void main(String[] args) throws Exception {

        List<String> strings = readListPage(path, 9999, 10000);
//        List<String> strings = readForPage(new File(path), 1001, 100);
        System.out.println(strings.size());
        System.out.println(strings.toString());
//        strings = readForPage(new File(path), 2, 100);
//        System.out.println(strings.toString());
//        strings = readForPage(new File(path), 1002, 100);
//        System.out.println(strings.toString());
    }

    private static List<String> readListPage(String fileName, Integer offset, Integer limit)
            throws Exception {
        Path path = Paths.get(fileName);
        //读取文件
        Stream<String> lines = Files.lines(path);
        //流式操作，分片，构建对象
        return lines.skip(offset)
                .limit(limit)
                .map(s -> s)
                .collect(Collectors.toList());

    }

    private static void writeTestFile() {
        File file = new File(path);
        try {
            FileUtils.write(file, "", "UTF-8", false);
        } catch (IOException e) {
            logger.error("写入临时文件失败,文件名称:{}", file.getPath(), e);
        }

        StringBuilder sb = new StringBuilder();
        long phont = 13000000000L;
        int len = 10000;
        for (int i = 0; i < len; i++) {
            sb.append(phont++).append("\r\n");
        }

        try {
            FileUtils.write(file, sb.toString(), "UTF-8", true);
        } catch (IOException e) {
            logger.error("写入临时文件失败,文件名称:{}", file.getPath(), e);
        }
    }
}
