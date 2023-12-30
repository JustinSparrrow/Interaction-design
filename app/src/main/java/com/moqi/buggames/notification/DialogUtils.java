package com.moqi.buggames.notification;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtils {
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null) // 可以添加自定义的点击事件
                .show();
    }


}
