package org.flink.dataset;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.flink.pojo.People;

public class JoinFunctionDemo {

    public static void main(String[] args) throws Exception {

        //设置flink的执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<People> peopleDataSource = environment
            .readCsvFile("/Users/wubo/Documents/GitHub/flink_study/test.csv")
            .pojoType(People.class, "name", "age", "phone");

        DataSet<Tuple2<String, Double>> types = environment
            .readCsvFile("/Users/wubo/Documents/GitHub/flink_study/test1.csv")
            .types(String.class, Double.class);

        DataSet<People> nameDistinct = peopleDataSource.distinct("name");

        DataSet<Tuple2<String, Double>> with = nameDistinct.join(types).where("name").equalTo("f0")
            .with(new JoinFunction<People, Tuple2<String, Double>, Tuple2<String, Double>>() {
                @Override
                public Tuple2<String, Double> join(People people, Tuple2<String, Double> t)
                    throws Exception {
                    System.out.println(people.age + ":" + t.f1);
                    return new Tuple2<>(people.name, people.age * t.f1);
                }
            });

        with.print();

    }
}
