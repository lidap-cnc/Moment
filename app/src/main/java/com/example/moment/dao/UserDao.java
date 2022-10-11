package com.example.moment.dao;


import com.example.moment.dbutils.DBUtils;
import com.example.moment.dto.UserInfo;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    private static Connection conn;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    private static int isLogin = 0;//登录是否成功的判断标志: 0是未登录,1是登录成功,2是登录失败
    private static UserInfo user = null;

    /**
     * 查询某用户是否存在, 根据用户名查找
     * @param username 用户名
     * @return true表示存在, false表示不存在
     */
    public static boolean findUser(String username){
        try {
            String logSql = "Select * from users where username= '"+username+"'  ";

            conn = DBUtils.getConnection();

            System.out.println("------"+conn);//测试

            preparedStatement  = conn.prepareStatement(logSql);

            rs = preparedStatement.executeQuery(logSql);

            // 获取跳转判断
            if(rs.next()){
                return true;//存在该用户

            }else{
                return false;//不存在该用户
            }
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            //关闭数据库连接
            DBUtils.close(rs,preparedStatement,conn);
        }
        return false;
    }

    /**
     * 用户登录SQL代码
     * @param username 用户名
     * @param password 密码
     */
    public static int UserLogin(String username,String password){
        try {
            String logSql = "Select * from users where username= '"+username+"' and password= '"+password+"' ";

            conn = DBUtils.getConnection();

            preparedStatement  = conn.prepareStatement(logSql);

            rs = preparedStatement.executeQuery(logSql);

            // 获取跳转判断
            if(rs.next()){
                isLogin = 1;
                user = new UserInfo();
                user.setNickname(rs.getString("nickname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getInt("sex"));

            }else{
                isLogin=2;
            }

            if(isLogin == 1)
                DBUtils.close(rs,preparedStatement,conn);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return isLogin;
    }

    /**
     * 用户注册SQL代码
     * @param nickname 昵称
     * @param username 用户名
     * @param password 密码
     * @param sex 性别: 0表示女生, 1表示男生
     */
    public static void UserRegister(String nickname,String username,String password,int sex){
        boolean flag = false;
        try {
            String logSql = "insert into users(nickname,username,password,sex) VALUES ('"+nickname+"','"+username+"','"+password+"',"+sex+")";

            conn = DBUtils.getConnection();

            preparedStatement  = conn.prepareStatement(logSql);

            int i = preparedStatement.executeUpdate();  // 如果i>0，表示DML操作是成功的；如果i=0表示DML操作对数据表中的数据没有影响
            flag = i>0;
            System.out.println("-----flag: "+flag);
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtils.close(rs,preparedStatement,conn);
        }
    }

    /**
     * 获取登录状态
     * @return 返回一个登录状态
     */
    public static int getIsLogin() {
        return isLogin;
    }

    /**
     * 获取登录成功用户的昵称,用于登录成功之后显示
     * @return 用户昵称
     */
    public static String getNickname(){
        return user.getNickname();
    }
}


