package org.flink.sink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.flink.pojo.Student;
import org.flink.source.SourcezFromMysql;

public class TestSinkToMysql {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Student> studentDataStreamSource =
            environment.addSource(new SourcezFromMysql());

        SingleOutputStreamOperator<Student> map = studentDataStreamSource.map(new MapFunction<Student, Student>() {

            @Override
            public Student map(Student student) throws Exception {
                return student;
            }
        });

        map.addSink(new SinkToMysql());

        environment.execute("test");
    }
}
