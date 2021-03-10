package com.wen.awenboot;

import com.wen.awenboot.utils.DruidPwdConfigTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AwenbootApplication {

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("pwd")) {
                String[] params = {args[1]};
                try {
                    DruidPwdConfigTools.main(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }

        SpringApplication.run(AwenbootApplication.class, args);
    }

}
