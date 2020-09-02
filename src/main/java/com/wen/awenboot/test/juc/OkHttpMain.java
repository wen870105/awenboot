package com.wen.awenboot.test.juc;

import com.wen.awenboot.common.ThreadPoolThreadFactoryUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wen
 * @version 1.0
 * @date 2020/9/2 0:20
 */
@Slf4j
public class OkHttpMain {
    private static ExecutorService executor = new ThreadPoolExecutor(32, 64, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10240),
            ThreadPoolThreadFactoryUtil.nameThreadFactory("okhttp-pool"));

    public static void main(String[] args) throws IOException {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("onFailure", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                log.info("onResponse ={}", response.body().string());
            }
        });

        System.in.read();
    }

    private void sync() {
//        http://117.128.1.49:7011/recommendService/services/proQuery/102/004050160005/01/13912922849

        String url = "https://www.baidu.com/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            log.debug("response:{}",response.body().string());
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
