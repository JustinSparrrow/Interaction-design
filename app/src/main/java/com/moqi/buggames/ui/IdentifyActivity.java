package com.moqi.buggames.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.moqi.buggames.R;
import com.moqi.buggames.dictionary.AnimalNameManager;
import com.moqi.buggames.identify.Animal;
import com.moqi.buggames.notification.DialogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IdentifyActivity extends AppCompatActivity {

    // 相机权限请求代码
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    // 相机请求代码
    private static final int CAMERA_REQUEST_CODE = 101;
    // 相册请求代码
    private static final int GALLERY_REQUEST_CODE = 102;

    private ImageButton linkButton;
    private Button captureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify_activity);

        linkButton = findViewById(R.id.linkButton);
        captureButton = findViewById(R.id.button2);

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    openCamera();
                } else {
                    requestCameraPermission();
                }
            }
        });
    }

    // 打开相册
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    // 检查相机权限
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    // 请求相机权限
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    // 打开相机
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    // 处理权限请求的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 处理图像信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                // 处理从相机拍摄的图片
                Uri selectedImage = data.getData();
                String imagePath = getImagePath(selectedImage);
                if(imagePath != null){
                    String name = Animal.animal(imagePath);
                    if (name != null) {
                        AnimalNameManager.saveAnimalName(this, name);
                        // 显示弹窗，展示识别出的动物名称
                        showAnimalNameDialog(name);
                    } else {
                        // 处理识别失败的情况，弹出错误提示
                        showErrorDialog("无法识别动物");
                    }
                } else {
                    showAnimalNameDialog("无法识别");
                }

            } else if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                // 这里可以处理拍照后的操作
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // 提供一个标题，这里使用当前时间作为标题
                String title = String.valueOf(System.currentTimeMillis());

                // 将 Bitmap 保存到文件中并添加到相册
                String imagePath = saveBitmapToFile(imageBitmap, title);

                String name = Animal.animal(imagePath);

                if (name != null) {
                    AnimalNameManager.saveAnimalName(this, name);
                    // 显示弹窗，展示识别出的动物名称
                    showAnimalNameDialog(name);
                } else {
                    // 处理识别失败的情况，弹出错误提示
                    showErrorDialog("无法识别动物");
                }
            }
        }
    }

    // 显示动物名称的弹窗
    private void showAnimalNameDialog(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("识别结果")
                .setMessage("识别出的动物为：" + name)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 在用户点击“OK”时执行操作
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    // 显示错误信息的弹窗
    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 在用户点击“OK”时执行操作
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    // 将Bitmap保存到文件中
    private String saveBitmapToFile(Bitmap imageBitmap, String title) {
        // 声明变量
        String imageFilePath = "";

        // 保存文件到应用私有目录
        File imageFile = new File(getFilesDir(), title + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // 获取文件路径
            imageFilePath = imageFile.getAbsolutePath();

            // 将文件添加到相册
            MediaStore.Images.Media.insertImage(getContentResolver(), imageFilePath, title, null);

            // 刷新相册
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFilePath;
    }

    // 获取图片文件路径
    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }


}
