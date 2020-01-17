package org.flink.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @Author: wubo
 * @Date: 2020/1/17 14:19
 */
public class FlinkWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", "spark01:9092");
        props.put("zookeeper.connect", "spark01:2181");
        props.put("group.id", "test-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");//value 反序列化
        props.put("auto.offset.reset", "latest");
        DataStreamSource<String> lines = environment.addSource(new FlinkKafkaConsumer<String>("frist", new SimpleStringSchema(),
                props));
        SingleOutputStreamOperator<Wordwithcount> pairs=lines.flatMap(new FlatMapFunction<String, Wordwithcount>() {
            @Override
            public void flatMap(String s, Collector<Wordwithcount> out) throws Exception {
                 String[] splits=s.split(" ");
                 for(String word:splits){
                     out.collect(new Wordwithcount(word,1));
                 }
            }
        });
        //使用keyby算子 把相同的key分到一个区
        KeyedStream<Wordwithcount, Tuple> wordcountskeyby=pairs.keyBy("word");

        WindowedStream<Wordwithcount, Tuple, TimeWindow> wordwithcountTupleTimeWindowWindowedStream = wordcountskeyby.timeWindow(Time.seconds(3), Time.seconds(2));

        SingleOutputStreamOperator<Wordwithcount> count = wordwithcountTupleTimeWindowWindowedStream.sum("count");

        count.print();

        environment.execute("test wordcount");

    }

    public static class Wordwithcount{
        public String word;
        public Integer count;

        public Wordwithcount(){}
        public Wordwithcount(String word,Integer count){
            this.word=word;
            this.count=count;
        }

        @Override
        public String toString() {
            return "Wordwithcount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

}
