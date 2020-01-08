package org.flink.demo;

import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;

public class FristFlinkDemo {

    public static void main(String[] args) {

        //1 初始化flink执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //2 读取指定路径的文本文件
        DataStream<String> dataStream=env.readTextFile("/Users/wubo/Documents/GitHub/flink_study/test.txt");
        //3 action算子 对DataStream中的数据打印
        dataStream.print();
        //4 启动flink应用
        env.execute("test");

    }
}