package com.example.moment.dbutils;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://10.0.2.2:3306/moment?useUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String pwd = "@2002Lwh";

    private static Connection conn=null;


    /**
     * 注册驱动只需执行一次，因此我们放在帮助类的静态初始化块中完成
     */
    static{
        try {
            Class.forName(driver);
            Log.e("驱动","-----------注册驱动成功");
        } catch (ClassNotFoundException e) {
            Log.e("驱动","-----------注册驱动失败");
        }
    }

    /**
     * 创建数据库连接对象
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
            Log.e("连接数据库: ","成功！dbutils");

        } catch (SQLException e) {
            //System.out.println("-----------创建连接失败");
            Log.e("连接数据库: ","失败！dbutils");
        }
        return connection;
    }

    /**
     * 关闭连接
     * 多态的应用：使用Statement接口做参数，既可以传递Statement接口对象，也可以传递PreparedStatement接口对象
     */
    public static void close(PreparedStatement statement, Connection connection){
        close(null,statement,connection);
    }

    /**
     * 关闭连接
     */
    public static void close(ResultSet resultSet, PreparedStatement statement, Connection connection){
        try {
            if(resultSet!=null && !resultSet.isClosed()){
                resultSet.close();
            }
            if(statement!=null && !statement.isClosed()){
                statement.close();
            }
            if(connection!=null && !connection.isClosed()){
                connection.close();
            }
        }catch (Exception e){
            System.out.println("~~~~~关闭数据库连接失败");
        }
    }
}


