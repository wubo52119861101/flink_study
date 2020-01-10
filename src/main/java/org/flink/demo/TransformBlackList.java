package org.flink.demo;

import java.util.ArrayList;
import java.util.List;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.JoinedStreams;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 基于transform的实时广告计费黑名单过滤
 */
public class TransformBlackList {

    public static class LogDate{

        public String username;

        public String date;

        public LogDate(){}

        public LogDate(String username,String date){

            this.username=username;
            this.date=date;
        }

        @Override
        public String toString() {
            return username+" "+date;
        }
    }

    public static void main(String[] args)throws Exception {

        //设置flink执行环境
        StreamExecutionEnvironment environment=StreamExecutionEnvironment.getExecutionEnvironment();

        //生成一份模拟的黑名单
        List<Tuple2<String,Boolean>> blackList=new ArrayList<>();
        blackList.add(new Tuple2<String,Boolean>("tom",true));
        DataStream<Tuple2<String,Boolean>> blackListStream=environment.fromCollection(blackList);
        //生成DataStream 从socket中读取模拟的日志 格式 date username的形式
        DataStream<String> dataStreamLog=environment.socketTextStream("localhost",9999);
        //对于输入的数据进行转换操作变成(username,data username)的格式，与定义好的黑名单进行join
        SingleOutputStreamOperator<LogDate> userAdsClickLogStream=dataStreamLog.map(new MapFunction<String, LogDate>() {
            @Override
            public LogDate map(String s) throws Exception {
                return new LogDate(s.split(" ")[1],s);
            }
        });
        //然后就可以执行transform操作了执行join操作
       //JoinedStreams<> userAdsClickLogStream.join(blackListStream);

        environment.execute("test");

    }
}
