package cn.spark;

import org.apache.http.client.methods.RequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

/**
 * Created by admin on 2018/1/26.
 */
public class ElasticSearchSmartcn {

    public static void main(String[] args) {

        TransportClient client = ElasticSearchUtil.cilentFactory();

        SearchRequestBuilder srb = client.prepareSearch("film2").setTypes("dongzuo");
        //组合多条件查询01
      //  QueryBuilder requestBuilder = QueryBuilders.matchQuery("desc", "非洲迷宫").analyzer("smartcn");

        QueryBuilder requestBuilder = QueryBuilders.multiMatchQuery("星球大战非洲迷宫","title","desc").analyzer("smartcn");
        SearchResponse response = srb.setQuery(requestBuilder).setFetchSource(new String[]{"title", "desc"}, null).execute().actionGet();
        SearchHits hits = response.getHits();

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }


    }
}
