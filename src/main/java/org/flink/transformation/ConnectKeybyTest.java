package org.flink.transformation;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wubo
 * @Date: 2020/1/17 15:30
 */
public class ConnectKeybyTest {
    public static void main(String[] args) {
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        List<String> list1=new ArrayList<>();
        list1.add("张三");
        List<Integer> list2=new ArrayList<>();
        list2.add(2);
        DataStreamSource<String> dataStreamSource1 = environment.fromCollection(list1);
        DataStreamSource<Integer> integerDataStreamSource2 = environment.fromCollection(list2);
        //这里的keyby 起到了什么作用？ 应用的场景是什么？
        ConnectedStreams<String, Integer> stringIntegerConnectedStreams = dataStreamSource1.connect(integerDataStreamSource2).keyBy(0, 1);

    }
}
