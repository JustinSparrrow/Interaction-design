package com.moqi.buggames.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.moqi.buggames.R;

public class UserActivity extends AppCompatActivity {

    private TextView tv_username;
    private TextView tv_nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        tv_username = findViewById(R.id.tv_username);
        tv_nickname = findViewById(R.id.tv_nickname);

        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 取消退出登录操作
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_username.setText("未登录");
                                tv_nickname.setText("");
                                // 可以选择其他操作
                            }
                        })
                        .show();
            }
        });
    }
}