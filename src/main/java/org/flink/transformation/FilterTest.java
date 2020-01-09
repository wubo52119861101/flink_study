package org.flink.transformation;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FilterTest {

    public static void main(String[] args)throws Exception{

        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream=environment.readTextFile("/Users/wubo/Documents/GitHub/flink_study/test.txt");

        //filter算子对读取进来的数据进行过滤，对每个元素都进行判断，返回true的元素，如果返回false则丢弃该数据
        DataStream<String> filterStream=dataStream.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {
                if(s.contains("zookeeper")){

                    return false;
                }
                return true;
            }
        });

        filterStream.print();

        environment.execute("test");
    }
}
