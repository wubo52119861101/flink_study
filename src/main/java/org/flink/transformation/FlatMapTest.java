package org.flink.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class FlatMapTest {

    public static void main(String[] args) throws Exception{

        //设置flink执行环境
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        //创建一个DataStream 从文本文件中读取数据
        DataStream<String> dataStream=environment.readTextFile("/Users/wubo/Documents/GitHub/flink_study/test.txt");
        DataStream<String> flatMapStream=dataStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> out) throws Exception {
                out.collect(s);
            }
        });

        flatMapStream.print();

        environment.execute("test");
    }
}
