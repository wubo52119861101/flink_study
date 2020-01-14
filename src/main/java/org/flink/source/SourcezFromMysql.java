package org.flink.source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.flink.pojo.Student;
import org.flink.util.MySqlUtil;

public class SourcezFromMysql extends RichSourceFunction<Student> {

    PreparedStatement ps;

    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection= MySqlUtil.getConnection("com.mysql.jdbc.Driver",
            "jdbc:mysql://rm-2zeqf4x7d2l30y0mr9o.mysql.rds.aliyuncs.com/mytest",
            "wubo","qazwsx123!");
        String sql="select * from student";
        ps=this.connection.prepareStatement(sql);
    }

    @Override
    public void close() throws Exception {
        super.close();
        if(connection!=null){

            connection.close();
        }
        if(ps!=null){

            ps.close();
        }
    }



    @Override
    public void run(SourceContext<Student> ctx) throws Exception {

        ResultSet rs=ps.executeQuery();

        while(rs.next()){

            Student student=new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getInt("age")
            );

            ctx.collect(student);

        }
    }

    @Override
    public void cancel() {

    }
}
