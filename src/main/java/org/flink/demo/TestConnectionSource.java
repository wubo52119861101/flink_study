package org.flink.demo;

import java.util.ArrayList;
import java.util.List;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.streaming.api.scala.DataStream;

public class TestConnectionSource {

    public static void main(String[] args) throws Exception {

        // 初始化flink执行环境
        ExecutionEnvironment executionEnvironment=ExecutionEnvironment.getExecutionEnvironment();

        List list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        DataSource<List> dataStream=executionEnvironment.fromCollection(list);

        dataStream.print();

        // 这个writeAsCsv方法只能使用在一个tuple的数据集上
        //dataStream.writeAsCsv("/Users/wubo/Documents/GitHub/flink_study/test_fromcollection.txt");
        dataStream.writeAsText("/Users/wubo/Documents/GitHub/flink_study/test_fromcollection.txt");

        //执行flink应用
        executionEnvironment.execute("test");

    }
}
