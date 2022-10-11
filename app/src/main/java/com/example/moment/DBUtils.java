package com.example.moment;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtils {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://10.0.2.2:3306/moment?useUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String pwd = "@2002Lwh";

    private static Connection conn=null;
    private static int jumper=111;
    private static String userid;
    private static String nickname;

    static{
        try {
            Class.forName(driver);
            Log.e("驱动加载: ","成功！");//测试用的
        }
        catch (Exception e){
            Log.e("驱动加载: ","失败！");
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url, user, pwd);
            Log.e("连接数据库: ","成功！");
        }
        catch (Exception e){
            Log.e("连接数据库: ","失败！");
            e.printStackTrace();
        }
    }


    public static void linkLoginsql(String username, String password) {
        try {
            //验证是否用户名以及密码是否正确
            String logSql = "Select * from users where username='"+ username+ "'and password='"+ password+ "'";
            //判断连接是否失效，失效则重新获取连接
            if(!conn.isValid(1)){
                conn = DriverManager.getConnection(url,user,pwd);
            }
            PreparedStatement stmt = conn.prepareStatement(logSql);

            ResultSet rs = stmt.executeQuery(logSql);

            // 获取跳转判断
            if(rs.next()){
                jumper=233;
                userid=rs.getString("username");
                nickname=rs.getString("nickname");
            }else{
                jumper=777;
            }
            System.out.println(jumper);
            rs.close();
            stmt.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        //关闭数据库
        if(conn!=null){
            try {

                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //获取跳转判断
    public static int getjumper(){
        return jumper;
    }
    //获取用户ID
    public static String getuserid(){
        return userid;
    }
    //获取用户昵称
    public static String getnickname(){
        return nickname;
    }
}

