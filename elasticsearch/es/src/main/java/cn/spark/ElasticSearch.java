package cn.spark;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.swing.text.Highlighter;

/**
 * Created by admin on 2018/1/26.
 */
public class ElasticSearch {

    public static void main(String[] args) {

        TransportClient client = ElasticSearchUtil.cilentFactory();

        SearchRequestBuilder srb = client.prepareSearch("user").setTypes("basic_info");

        //查询所有
        //SearchResponse response = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();

        //分页查询
        //  SearchResponse response = srb.setQuery(QueryBuilders.matchAllQuery()).setFrom(0).setSize(3).execute().actionGet();

        //排序
  /*      SearchResponse response = srb.setQuery(QueryBuilders.matchAllQuery()).addSort("age", SortOrder.ASC).execute().actionGet();*/

        //列过滤
        //SearchResponse response = srb.setQuery(QueryBuilders.matchAllQuery()).addSort("age", SortOrder.ASC).setFetchSource(new String[]{"name","age"},null).execute().actionGet();

        //简单条件查询
        // SearchResponse response = srb.setQuery(QueryBuilders.matchQuery("name","张三")).setFetchSource(new String[]{"name","age"},null).execute().actionGet();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<h3><em>");
        highlightBuilder.postTags("</em><h3>");
        highlightBuilder.field("name");

        //组合多条件查询01

        QueryBuilder b1 = QueryBuilders.matchPhraseQuery("name", "张三");
        QueryBuilder b2 = QueryBuilders.matchPhraseQuery("address", "山东");
        QueryBuilder b3 = QueryBuilders.rangeQuery("age").gt(20);
        // SearchResponse response = srb.setQuery(QueryBuilders.boolQuery().must(b1).must(b2)).execute().actionGet();
        //SearchResponse response = srb.setQuery(QueryBuilders.boolQuery().must(b1).mustNot(b2)).execute().actionGet();
        // SearchResponse response = srb.setQuery(QueryBuilders.boolQuery().must(b1).must(b2).should(b3)).addSort("age",SortOrder.DESC).execute().actionGet();
        SearchResponse response = srb.setQuery(QueryBuilders.boolQuery().must(b1).must(b2).should(b3).filter(b3)).execute().actionGet();
        SearchHits hits = response.getHits();

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            //  System.out.println(hit.getHighlightFields());
        }


    }
}
