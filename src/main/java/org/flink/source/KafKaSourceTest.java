package org.flink.source;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/*
利用flink自带的source读取kafka里面的数据
 */
public class KafKaSourceTest {
    public static void main(String[] args) throws Exception {
        //设置flink执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", "spark01:9092");
        props.put("zookeeper.connect", "spark01:2181");
        props.put("group.id", "test-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");//value 反序列化
        props.put("auto.offset.reset", "latest");
        DataStreamSource<String> dataStreamSource=environment.addSource(new FlinkKafkaConsumer<String>("frist",new SimpleStringSchema(),
                props)).setParallelism(1);
        dataStreamSource.print();
        environment.execute("testKafka");

    }
}
