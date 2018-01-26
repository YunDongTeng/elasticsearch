package cn.spark;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/25.
 */
public class ElasticSearchFilm {

    public static void main(String[] args) throws Exception {


        File file = new File("E:\\es数据.txt");

        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<JsonObject> list = new ArrayList<JsonObject>(10);

        String lineData = null;
        String[] dataArray = null;
        while ((lineData = reader.readLine()) != null) {
            dataArray = lineData.split(",");
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("title", dataArray[0]);
            jsonObject.addProperty("publishDate", dataArray[1]);
            jsonObject.addProperty("director", dataArray[2]);
            jsonObject.addProperty("price", dataArray[3]);
            jsonObject.addProperty("desc", dataArray[4]);

            list.add(jsonObject);

        }

        TransportClient client = ElasticSearchUtil.cilentFactory();
        list.stream().forEach(jsonObject -> {

            System.out.println(jsonObject.toString());

            IndexResponse response = client.prepareIndex("film2", "dongzuo").setSource(jsonObject.toString(), XContentType.JSON).get();

            System.out.println("status:"+response.status());
            System.out.println("index:"+response.getIndex());
            System.out.println("ID:"+response.getId());
            System.out.println("type:"+response.getType());
        });

        fileReader.close();
        reader.close();

    }
}
