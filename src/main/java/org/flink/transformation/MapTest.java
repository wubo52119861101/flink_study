package org.flink.transformation;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MapTest {

    public static void main(String[] args) throws Exception {

        //初始化flink执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        //创建DataStream 并从文本中读取数据 按照一行一行的读取
        DataStream<String> dataStream = environment.readTextFile("/Users/wubo/Documents/GitHub/flink_study/test.txt");

        //Map算子，把读取出来的每行数据进行转换，这里的MapFunction<String, String>
        //第一个String代表输入的类型，第二个String代表输出的类型
        DataStream<String> mapDataStream=dataStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String line) throws Exception {
                //这里的line代表输入的数据
                return line+"加上的数据";
            }
        });
        mapDataStream.print();
        //必须执行flink程序才会真正运行
        environment.execute("text");
    }
}
