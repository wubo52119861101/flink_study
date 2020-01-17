package org.flink.transformation;

import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wubo
 * @Date: 2020/1/17 16:00
 */
public class SplitTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<=10;i++){
            list.add(i);
        }

        DataStreamSource<Integer> integerDataStreamSource = environment.fromCollection(list);

        SplitStream<Integer> split = integerDataStreamSource.split(new OutputSelector<Integer>() {

            @Override
            public Iterable<String> select(Integer integer) {
                List<String> output=new ArrayList<>();
                if(integer%2==0){
                    output.add("even");
                }else {
                    output.add("odd");
                }
                return output;
            }
        });
        //接下来筛选出新的数据集
        DataStream<Integer> even = split.select("even");
        even.print();
        environment.execute("test");
    }
}
