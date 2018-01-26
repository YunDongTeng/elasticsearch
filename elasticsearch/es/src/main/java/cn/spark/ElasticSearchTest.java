package cn.spark;

import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by admin on 2018/1/25.
 */
public class ElasticSearchTest {

    public static void main(String[] args) {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", "王五ggg");
        jsonObject.addProperty("age", 22);
        jsonObject.addProperty("address", "江西南昌");
        jsonObject.addProperty("email", "zhagnsan_smile@163.com");

        TransportClient client = ElasticSearchUtil.cilentFactory();

        //创建索引
        IndexResponse response = client.prepareIndex("user", "basic_info").setSource(jsonObject.toString(), XContentType.JSON).get();

        System.out.println("索引：" + response.getIndex());
        System.out.println("类型：" + response.getType());
        System.out.println("id:" + response.getId());
        System.out.println("状态" + response.status());



    }
}
