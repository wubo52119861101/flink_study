package org.flink.transformation;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: wubo
 * @Date: 2020/1/17 15:12
 */
public class ConnectTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment= StreamExecutionEnvironment.getExecutionEnvironment();
        List<String> list1=new ArrayList<>();
        list1.add("张三");
        List<Integer> list2=new ArrayList<>();
        list2.add(2);
        DataStreamSource<String> dataStreamSource1 = environment.fromCollection(list1);
        DataStreamSource<Integer> integerDataStreamSource2 = environment.fromCollection(list2);
        //在这里不能直接进行类似print的操作，需要转换为DataStream才可以
        ConnectedStreams<String, Integer> connect = dataStreamSource1.connect(integerDataStreamSource2);
        //那么该如何转换为DataStream呢？
        SingleOutputStreamOperator<Object> map = connect.map(new CoMapFunction<String, Integer, Object>() {
            //在这里，会有两个线程交替执行输出结果
            @Override
            public Object map1(String s) throws Exception {
                return s;
            }

            @Override
            public Object map2(Integer integer) throws Exception {
                return integer;
            }
        });
        map.print();
        environment.execute("test connect");
    }
}
