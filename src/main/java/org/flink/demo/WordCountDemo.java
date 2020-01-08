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

public class WordCountDemo {

    public static class WordWithCount{

        public String word;

        public Long count;

        public WordWithCount(){}

        public WordWithCount(String word,Long count){

            this.word=word;
            this.count=count;
        }

        @Override
        public String toString() {
            return word+" : "+count;
        }
    }

    public static void main(String[] args)throws Exception {

        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream=environment.readTextFile("/Users/wubo/Documents/GitHub/flink_study/test.txt");


        //开始计算 生成一个个的元组 word,1
        SingleOutputStreamOperator<WordWithCount> pairsWords=dataStream.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String s, Collector<WordWithCount> out) throws Exception {

                String[] splits=s.split(" ");
                for(String word:splits){

                    out.collect(new WordWithCount(word,1L));
                }
            }
        });

        //将元组按照key 进行分组，将所有数据中包含该key的数据都发送到同一个分区上
        KeyedStream<WordWithCount, Tuple> grouped=pairsWords.keyBy("word");

        //调用窗口操作
        //timeWindow时间窗口函数，以界定对多长时间内的数据做统计
        //需要给两个重要的参数:窗口长度和滑动间隔
        WindowedStream<WordWithCount,Tuple, TimeWindow> window=grouped.timeWindow(Time.seconds(3),Time.seconds(2));
        //求和函数， 表示对二元组中第二个元素求和，因为经过前面的keyBy,所有的单词都被发到同一个分区上，因此在一个分区上
        //将单词出现的次数进行累加，得到的就是单词出现的总次数
        SingleOutputStreamOperator<WordWithCount> counts=window.sum("count");

        //打印
        counts.print();
        environment.execute("wordcount");

    }

}
