package com.wen.awenboot.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 15:05
 */
public class ReadFilePageUtil {

    
    public static List<String> readListPage(String fileName, Integer offset, Integer limit)
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
}
