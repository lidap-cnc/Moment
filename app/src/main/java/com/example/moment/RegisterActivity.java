package com.example.moment;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moment.dao.UserDao;
import com.example.moment.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private boolean findUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //返回登录按钮
        Button re_backLogin = findViewById(R.id.re_backLogin);
        //点击返回登录按钮触发返回登录事件
        re_backLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //跳转到返回登录界面
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

        //注册按钮
        Button re_register = findViewById(R.id.re_register);
        //当点击注册按钮，开始执行注册操作
        re_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定组件
                EditText nickname = findViewById(R.id.re_nickname);//昵称
                EditText username = findViewById(R.id.re_username);//用户名
                EditText password = findViewById(R.id.re_password);//密码
                EditText passwordAffirm = findViewById(R.id.re_affirm);//二次确认密码

                //将用户输入的信息取出并转成String类型
                String inputNickname = nickname.getText().toString();
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();
                String inputAffirm = passwordAffirm.getText().toString();


                //在线程中调用数据库,根据用户名查询,查看是否已存在该用户,将查询结果赋值给findUser
                Thread t1 = new Thread(new Runnable() {
                    public void run() {
                        findUser = UserDao.findUser(inputUsername);
                    }
                });
                t1.start();
                //在数据库连接完成之前暂停其他活动
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //如果为true则说明已存在该用户,不能进行注册,否则开始注册
                if (findUser){
                    Toast.makeText(RegisterActivity.this,"该账号已存在!!", Toast.LENGTH_SHORT).show();
                } else if (inputAffirm.equals(inputPassword)) {//判断两次输入的密码是否一致
                    //创建账号密码
                    if (username.length()<2 || username.length()>10) {
                        Toast.makeText(RegisterActivity.this,"账号长度需要大于2小于10位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (inputPassword.length()<6 || inputPassword.length()>15) {
                        Toast.makeText(RegisterActivity.this,"密码长度需要大于5小于15位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(inputNickname.length()<2 || inputNickname.length()>10) {
                        Toast.makeText(RegisterActivity.this,"昵称长度需要大于5小于10位", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Thread t2 = new Thread(new Runnable() {
                        public void run() {
                            UserDao.UserRegister(inputNickname,inputUsername,inputPassword,1);
                        }
                    });
                    t2.start();
                    //在数据库连接完成之前暂停其他活动
                    try {
                        t2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //查看注册后数据有没有成功插入数据库,赋值给findUser
                    t1 = new Thread(new Runnable() {
                        public void run() {
                            findUser = UserDao.findUser(inputUsername);
                        }
                    });
                    t1.start();
                    //在数据库连接完成之前暂停其他活动
                    try {
                        t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //如果存在该用户,则说明注册成功,否则注册失败
                    if (findUser){
                        Toast.makeText(RegisterActivity.this,"注册成功!!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this,"注册失败,请重试!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
