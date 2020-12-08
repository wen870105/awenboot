package com.wen.awenboot.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author wen
 * @version 1.0
 * @date 2020/12/7 13:50
 */
@Slf4j
public class ShellUtils {

    public static void main(String[] args) {
        String cmd = "d:/dir.bat wen yong";
        invokeShell(cmd);
    }

    public static void invokeShell(String cmd) {
//方法1 执行字符串命令（各个参数1234之间需要有空格）
//        String path = "sh /root/zpy/zpy.sh 1 2 3 4";
//方法2 在单独的进程中执行指定命令和变量。
//第一个变量是sh命令，第二个变量是需要执行的脚本路径，从第三个变量开始是我们要传到脚本里的参数。
//        String[] path = new String[]{"sh", "/root/zpy/zpy.sh", "1", "2", "3", "4"};
        try {
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(cmd);
            int status = pro.waitFor();
            if (status != 0) {
                log.info("[执行脚本]失败:{}", cmd);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(new String(line.getBytes("UTF-8"), "GBK")).append("\n");
            }

            String result = sb.toString();

            log.info("[执行脚本]结果:{}", sb.toString());
        } catch (Exception e) {
            log.error("[执行脚本]异常", e);
        }
    }
}
