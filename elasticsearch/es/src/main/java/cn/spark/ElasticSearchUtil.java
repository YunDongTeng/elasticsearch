package cn.spark;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

/**
 * Created by admin on 2018/1/25.
 */
public class ElasticSearchUtil {

    private static final String host = "192.168.15.38";

    private static final int port = 9300;
    private static TransportClient client = null;

    private static final String CLUSTER_NAME = "my-application";

    private static Settings.Builder builder = Settings.builder().put("cluster.name", CLUSTER_NAME);

    public static TransportClient cilentFactory() {
        client = new PreBuiltTransportClient(builder.build()).addTransportAddress(new TransportAddress(new InetSocketAddress(host, port)));
        return client;
    }

    public static void close() {
        if (client != null) {
            client.close();
        }
    }
}
