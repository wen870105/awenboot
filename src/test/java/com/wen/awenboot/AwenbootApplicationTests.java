package com.wen.awenboot;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class AwenbootApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) throws IOException {
        String s = FileUtils.readFileToString(new File("D:\\test_12334511.txt"), "UTF-8");
        System.out.println(s);

        FileUtils.write(new File("D:\\test_12334511.txt"), "18112345678\r\n18112345679");

        String s1 = FileUtils.readFileToString(new File("D:\\test_12334511.txt"), "UTF-8");
        System.out.println(s1);
    }

}
