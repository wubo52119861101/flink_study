package org.flink.demo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.stream.Stream;

public class WordCountDemo {

    public static class WordWithCount {

        public String word;

        public Long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, Long count) {

            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();
        //从socket中读取单词
        DataStream<String> dataStream=environment.socketTextStream("localhost",9999);
        SingleOutputStreamOperator<WordWithCount> parisWords=dataStream.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String s, Collector<WordWithCount> out) throws Exception {
                String[] splits=s.split(" ");
                for(String word:splits){

                    out.collect(new WordWithCount(word,1L));
                }
            }
        });
        KeyedStream<WordWithCount, Tuple> words = parisWords.keyBy("word");
        WindowedStream<WordWithCount, Tuple, TimeWindow> wordWithCountTupleTimeWindowWindowedStream = words.timeWindow(Time.seconds(5), Time.seconds(3));
        SingleOutputStreamOperator<WordWithCount> count = wordWithCountTupleTimeWindowWindowedStream.sum("count");
        count.print();
        environment.execute("wordcount");
    }
}
