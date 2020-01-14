package org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestJdbc {

    public static String driver="com.mysql.jdbc.Driver";
    public static String url="jdbc:mysql://rm-2zeqf4x7d2l30y0mr9o.mysql.rds.aliyuncs.com/mytest?useSSL=false";
    public static String username="wubo";
    public static String password="qazwsx123!";

    public static void main(String[] args) throws Exception {

        Class.forName(driver);
        Connection connection= DriverManager.getConnection(url,username,password);

        PreparedStatement preparedStatement=null;

        String sql="insert into student2(id,name,password,age)values(?,?,?,?)";

        preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setInt(1,1);
        preparedStatement.setString(2,"张三");
        preparedStatement.setString(3,"1234");
        preparedStatement.setInt(4,18);

        int len=preparedStatement.executeUpdate();

        System.out.println(len);
    }
}
