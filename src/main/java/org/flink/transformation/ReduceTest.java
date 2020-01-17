package org.flink.transformation;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wubo
 * @Date: 2020/1/17 14:40
 */
public class ReduceTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        //在这个案例里，两个数据流的数据类型必须完全相同
        List<Tuple2<String,Boolean>> backList=new ArrayList<>();
        backList.add(new Tuple2<>("tom",true));
        List<Tuple2<String,Boolean>> backList2=new ArrayList<>();
        backList2.add(new Tuple2<>("jack",false));
        DataStreamSource<Tuple2<String, Boolean>> tuple2DataStreamSource = environment.fromCollection(backList);
        DataStreamSource<Tuple2<String, Boolean>> tuple2DataStreamSource1 = environment.fromCollection(backList2);
        DataStream<Tuple2<String, Boolean>> union = tuple2DataStreamSource.union(tuple2DataStreamSource1);
        union.print();
        environment.execute("test");
    }
}
