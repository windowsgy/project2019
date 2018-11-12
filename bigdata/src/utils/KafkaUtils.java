package utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.List;
import java.util.Properties;

/**
 *
 * Created by jlgaoyuan on 2017/9/8.
 */
public class KafkaUtils {

    private static Properties props = new Properties();

    /**
     * 初始化
     * @param brokerSocket 设置kafka brokerSocket
     * @return boolean
     */
    public  boolean init  (String brokerSocket){
        try{
            props.put("bootstrap.servers", brokerSocket); //设置Brokers
            props.put("acks", "all");//应答
            props.put("retries ", 1); //重试次数
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            return true;
        }catch (Exception e){
            Log.info("Kafka 连接异常"+e.getMessage());
            return false;
        }
    }

    /**
     * sendTo kafka数据
     * @param topic toplic
     */
    public void sendToKafka(String topic,List<String> list ) {
        Log.linel4();
        Log.info("开始发送Kafka消息Topic:" + topic);
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (String aList : list) {
            producer.send(new ProducerRecord<>(topic, aList));
        }
        Log.info("发送Kafka消息记录数量" + list.size());
        producer.close();
    }
}
