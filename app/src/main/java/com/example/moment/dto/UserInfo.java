package com.example.moment.dto;


public class UserInfo {
    private int user_id;
    private String nickname;//昵称
    private String username;//用于登录的用户名
    private String password;//用户名密码
    private int sex;//性别,0:女,1:男


    public UserInfo() {
    }

    public UserInfo( String nickname, String username, String password, int sex) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

}


