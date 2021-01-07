package com.wen.awenboot.es;

import com.wen.awenboot.config.EsConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/6 11:06
 */
@Slf4j
@Service
public class EsClient {
    public final static int PORT = 9300;
    private TransportClient client = null;

    @Autowired
    private EsConfig esCfg;

    private String esType = "jh";


    @PostConstruct
    public void getConnect() throws UnknownHostException {
        String hosts = esCfg.getHosts();
        List<TransportAddress> list = new ArrayList<>();
        Settings settings = Settings.builder().put("cluster.name", esCfg.getClusterName()).build();
        PreBuiltTransportClient pre = new PreBuiltTransportClient(settings);
        if (hosts.contains(",")) {
            for (String hh : hosts.split(",")) {
                pre.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(hh), PORT));
            }
        } else {
            pre.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(hosts), PORT));
        }
        client = pre;
        log.info("连接信息：" + client.toString());
    }


    public void closeConnect() {
        if (null != client) {
            log.info("执行关闭连接操作");
            client.close();
        }
    }

    public long getKeyWord(String name, String val) throws IOException {
        SearchResponse searchResponse = build()
                .setQuery(QueryBuilders.wildcardQuery(name, "*" + val + "*"))
                .setSize(0)
                .get();
        // 获取命中次数，查询结果有多少对象
        return searchResponse.getHits().getTotalHits();
    }

    // 获取文档信息
    public void getIndexNoMapping() throws Exception {
        GetResponse actionGet = client.prepareGet(esCfg.getIndex(), esCfg.getType(), "AXTPZdRZRll7B1lvD6KO").execute().actionGet();
        System.out.println(actionGet.getSourceAsString());
    }


    // 查询所有文档信息
    public void getMatchAll() throws IOException {
        SearchResponse searchResponse = build().setQuery(QueryBuilders.matchAllQuery())
                .get();
        // 获取命中次数，查询结果有多少对象
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            // 查询每个对象
            SearchHit searchHit = iterator.next();
            System.out.println(searchHit.getSourceAsString()); // 获取字符串格式打印
            System.out.println("title:" + searchHit.getSource().get("name"));
        }
    }

    public SearchRequestBuilder build() {
        return client.prepareSearch(esCfg.getIndex()).setTypes(esCfg.getType());
    }

    public static void main(String[] args) throws Exception {
        EsClient client = new EsClient();
//        client.getIndexNoMapping();
//        client.getMatchAll();
//        client.getKeyWord();

        System.out.println(client.getKeyWord("name", "ya"));

    }

    // 关键字查询
    public void getKeyWord() throws IOException {
        long time1 = System.currentTimeMillis();
        SearchResponse searchResponse = build().setQuery(QueryBuilders.matchQuery("name", "yagao4"))
                .get();
        // 获取命中次数，查询结果有多少对象
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            // 查询每个对象
            SearchHit searchHit = iterator.next();
            // 获取字符串格式打印
            System.out.println(searchHit.getSourceAsString());
            System.out.println("title:" + searchHit.getSource().get("48310"));
        }
        long time2 = System.currentTimeMillis();
        System.out.println("花费" + (time2 - time1) + "毫秒");
    }
//
//    // 通配符、词条查询
//    @Test
//    public void getByLike() throws IOException {
//        long time1 = System.currentTimeMillis();
//        SearchResponse searchResponse = client.prepareSearch("blog1")
//                .setTypes("article").setQuery(QueryBuilders.wildcardQuery("desc", "你们*")) // 通配符查询
//                .setTypes("article").setQuery(QueryBuilders.wildcardQuery("content", "服务器"))
//                .setTypes("article").setQuery(QueryBuilders.termQuery("content", "全文")) // 词条查询
//                // 一般情况下只显示十条数据
//                // from + size must be less than or equal to: [10000]
//                // Scroll Search 支持1万以上的数据量
//                // .setSize(10000)
//                .get();
//        // 获取命中次数，查询结果有多少对象
//        SearchHits hits = searchResponse.getHits();
//        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
//        Iterator<SearchHit> iterator = hits.iterator();
//        while (iterator.hasNext()) {
//            // 查询每个对象
//            SearchHit searchHit = iterator.next();
//            // 获取字符串格式打印
//            System.out.println(searchHit.getSourceAsString());
//            System.out.println("title:" + searchHit.getSource().get("title"));
//        }
//        long time2 = System.currentTimeMillis();
//        System.out.println("花费" + (time2 - time1) + "毫秒");
//    }
//
//    // 组合查询
//    @Test
//    public void combinationQuery() throws Exception {
//        SearchResponse searchResponse = client.prepareSearch("blog1").setTypes("article")
//                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("title", "搜索"))// 词条查询
////                        .must(QueryBuilders.rangeQuery("id").from(1).to(5)) // 范围查询
//                                // 因为IK分词器，在存储的时候将英文都转成了小写
//                                .must(QueryBuilders.wildcardQuery("content", "Rest*".toLowerCase())) // 模糊查询
//                                .must(QueryBuilders.queryStringQuery("服电风扇丰盛的电器")) // 关键字（含有）
//                ).get();
//        SearchHits hits = searchResponse.getHits();
//        System.out.println("总记录数：" + hits.getTotalHits());
//        Iterator<SearchHit> iterator = hits.iterator();
//        while (iterator.hasNext()) {
//            SearchHit searchHit = iterator.next();
//            System.out.println(searchHit.getSourceAsString());
//            Map<String, Object> source = searchHit.getSource();
//            System.out.println(source.get("id") + ";" + source.get("title") + ";" + source.get("content"));
//        }
//

//    }

}
