package org.flink.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlUtil {

    public static Connection getConnection(String driver,String url,String username,String password){

        Connection conn=null;
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;

    }

    public static void main(String[] args) {

        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://rm-2zeqf4x7d2l30y0mr9o.mysql.rds.aliyuncs.com/mytest";
        String username="wubo";
        String password="qazwsx123!";

        Connection connection=getConnection(driver,url,username,password);

        System.out.println(connection);
    }
}
