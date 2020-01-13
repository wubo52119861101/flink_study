package org.flink.source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TestSourceFromMysql {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();

        environment.addSource(new SourcezFromMysql()).print();

        environment.execute("test");
    }
}
