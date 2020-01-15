package org.flink.transformation;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.flink.pojo.Student;
import org.flink.source.SourcezFromMysql;

public class AggregactionsTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Student> studentDateStream = environment.addSource(new SourcezFromMysql());
        //studentDateStream.print();
        SingleOutputStreamOperator<Integer> ids=studentDateStream.map(new MapFunction<Student, Integer>() {
            @Override
            public Integer map(Student student) throws Exception {
                return student.id;
            }
        });

        KeyedStream<Integer, Tuple> id = ids.keyBy("id");
        id.print();
        environment.execute("test");
    }
}
