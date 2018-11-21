package es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by jlgaoyuan on 2018/11/9.
 *
 */
public class ES_Client {

    /**
     *
     */
    public static void run(String clusterName,String server1,String server2,int port){
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        // on startup
        try{
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(server1), port))
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(server2), port));
        }catch (Exception e){
            e.printStackTrace();
        }
// on shutdown
        client.close();
    }
}
