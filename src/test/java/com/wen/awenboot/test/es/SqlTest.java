package com.wen.awenboot.test.es;//package com.wen.test.es;
//
//import java.net.InetAddress;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
//import com.alibaba.fastjson.JSON;
//
//public class SqlTest {
//	/**
//	 * 使用xpack客户端查询 体现在es集群安装了xpack,并有密码的情况下
//	 */
//	public static Map sqlQueryXpack() {
//		try {
//			Settings settings = Settings.builder().put("cluster.name", "cluster_name")
//					.put("xpack.security.user", "elastic:elastic_password") // x-pack的用户名及密码
//					.build();
//			TransportClient client = new PreBuiltXPackTransportClient(settings);
//			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//			// 创建sql查询对象
//			SearchDao searchDao = new SearchDao(client);
//			// 执行sql查询
//			SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao
//					.explain("select * from test_index where flag = 316").explain();
//			// 使用阿里巴巴的json格式化工具，resultMap的值就是查询返回的值
//			Map resultMap = (Map) JSON.parse((select.get()).toString());
//			return resultMap;
//		} catch (Exception ex) {
//			Log.low.error("查询异常", ex);
//		}
//
//	}
//
//	/**
//	 * 集群中没有安装xpack及未设置密码
//	 */
//	public static Map sqlQuery() {
//		try {
//			Settings settings = Settings.builder().put("cluster.name", "cluster_name").build();
//			TransportClient client = new PreBuiltTransportClient(settings);
//			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//			// 创建sql查询对象
//			SearchDao searchDao = new SearchDao(client);
//			// 执行sql查询
//			SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) searchDao
//					.explain("select * from test_index where flag = 316").explain();
//			// 使用阿里巴巴的json格式化工具，resultMap的值就是查询返回的值
//			Map resultMap = (Map) JSON.parse((select.get()).toString());
//			return resultMap;
//		} catch (Exception ex) {
//			Log.low.error("查询异常", ex);
//		}
//	}
//
//}
