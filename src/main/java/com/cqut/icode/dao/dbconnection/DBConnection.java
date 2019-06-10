package com.cqut.icode.dao.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 谭强
 * @date 2018/8/19
 */
public class DBConnection {
    private static Connection Connection = null;

    public static Connection getDBConnection() {
        if (Connection != null) {
            // 设为静态后运行过程中执行一遍就够了
            return Connection;
        }

        try {
            System.out.println("开始加载驱动。。。。");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动加载成功。。。。\n开始连接数据库");
            String url = "jdbc:mysql://localhost:3306/teacher?useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String password = "990106";
            Connection =  DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功。。。。");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载驱动失败！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！");
        }

        return Connection;
    }
}
