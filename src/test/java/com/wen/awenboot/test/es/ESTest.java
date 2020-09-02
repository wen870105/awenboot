package com.wen.awenboot.test.es;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.query.QueryAction;
import org.nlpcn.es4sql.query.SqlElasticSearchRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ESTest {
	private static final Logger logger = LoggerFactory.getLogger(ESTest.class);

	public static void main(String[] args) throws Exception {
		System.out.println("goooo");
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		try {
//			GetRequestBuilder prepareGet = client.prepareGet("users", "user", "1");
//			GetResponse test = prepareGet.get();
//			System.out.println(test.toString());
//
//			GetRequestBuilder p2 = client.prepareGet("users2", "user", "1");
//			GetResponse r2 = p2.get();
//			System.out.println(r2.toString());

			System.out.println("sql查询es::::");
			SearchDao dao = new SearchDao(client);
//			SqlElasticSearchRequestBuilder sql = (SqlElasticSearchRequestBuilder) dao
//					.explain("select * from migu where test").explain();
//			Map resultMap = (Map) JSON.parse((sql.get()).toString());
//			System.out.println(resultMap);

//			QueryAction qa = dao.explain("select region_name,count(*) from migu GROUP BY region_name.keyword");
//			SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) qa.explain();
//
//			SearchResponse response = (SearchResponse) select.get();
//			Aggregations aggregations = ((SearchResponse) select.get()).getAggregations();
//			Terms terms = aggregations.get("region_name.keyword");
//			List<KeyValuePair<String, Long>> list = new ArrayList<>();
//			terms.getBuckets().forEach(c -> {
//				list.add(KeyValuePair.build(c.getKey().toString(), c.getDocCount()));
//			});
//			System.out.println(JSON.toJSONString(list));
//			System.out.println("===" + response.toString());

//			test(dao);
//			test2222(client);
			test4444(client);
		} catch (Exception e) {
			logger.error("exception", e);
		} finally {
			client.close();
		}
		System.out.println("end");
	}

	/**
	 * @param client
	 * @throws Exception
	 */
	private static void test4444(TransportClient client) throws Exception {
		SearchRequestBuilder builder = client.prepareSearch("migu2").setTypes("jh");
		builder.addAggregation(AggregationBuilders.terms("Host_txt").field("Host.keyword"));
		builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		builder.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("Host")));
		SearchResponse searchResponse = builder.get();

		System.out.println("===" + JSON.toJSONString(searchResponse));
	}
	/**
	 * @param client
	 * @throws Exception
	 */
	private static void test2222(TransportClient client) throws Exception {
		SearchRequestBuilder builder = client.prepareSearch("migu2").setTypes("jh");
		builder.addAggregation(AggregationBuilders.stats("ageAgg").field("age"));
		builder.addAggregation(AggregationBuilders.cardinality("c_1").field("remoteAddr.keyword"));
		SearchResponse searchResponse = builder.get();
		System.out.println("===" + JSON.toJSONString(searchResponse));
	}

	private static void test(SearchDao dao) throws Exception {
		Map<String, Long> map = new HashMap<>();
		String sql = "SELECT distinct(*) FROM elasticsearch-sql_test_index/account GROUP BY gender,  terms('field'='age','size'=200,'alias'='age')";
		QueryAction qa = dao.explain(sql);

		SqlElasticSearchRequestBuilder select = (SqlElasticSearchRequestBuilder) qa.explain();

		System.out.println(select.toString());
		SearchResponse response = (SearchResponse) select.get();
		Aggregations aggregations = ((SearchResponse) select.get()).getAggregations();
		InternalDateHistogram terms = aggregations.get("remoteAddr.keyword");
		terms.getBuckets().get(0).getKeyAsString();
		System.out.println(JSON.toJSONString(terms));
//		terms.get
//		terms.getBuckets().forEach(c -> {			
//			map.get(c.getKeyAsString());
//			map.put(key, value);
//		});
		System.out.println(JSON.toJSONString(map));
		System.out.println("===" + response.toString());
	}
}
