package org.flink.sink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.flink.pojo.Student;
import org.flink.util.MySqlUtil;

public class SinkToMysql extends RichSinkFunction<Student> {

    PreparedStatement ps;

    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = getConnection();
        String sql = "insert into student2(name,password,age)values(?,?,?)";
        if (connection != null) {
            System.out.println("执行了sql语句");
           ps=connection.prepareStatement(sql);

        }
    }

    @Override
    public void close() throws Exception {
        super.close();

        if (connection != null) {

            connection.close();
        }

        if (ps != null) {

            ps.close();
        }
    }

    @Override
    public void invoke(Student value, Context context) throws Exception {

        if (ps == null) {
            System.out.println("ps=null 被return了");
            return;
        }
        //ps.setInt(1,value.id);
        ps.setString(1,value.name);
        ps.setString(2,value.password);
        ps.setInt(3,value.age);
        int len=ps.executeUpdate();
        System.out.println("受影响的行数"+len);
    }

    private Connection getConnection() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://rm-2zeqf4x7d2l30y0mr9o.mysql.rds.aliyuncs.com/mytest?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "wubo";
        String password = "qazwsx123!";
        Connection connection = MySqlUtil.getConnection(driver, url, username, password);
        return connection;
    }
}
