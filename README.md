# Interaction-design

---
## ui界面
**主界面设计：**  
![](主界面.png)  

**图鉴设计：**  
![](图鉴.png)  

**拍照设计：**  
![](拍照.png)  

**用户界面：**  
![](用户.png)

---
## 安卓开发技术文档
### drawable  
shape：
- rectangle模版（圆角矩形）
- background（背景颜色）
- 所要的组件图片的导入

### layout
布局文件用到的模版样式：
- androidx.constraintlayout.widget.ConstraintLayout
- RelativeLayout
- LinearLayout
- CardView
- Toolbar
- LinearLayoutCompat
- ScrollView
- GridLayout

### 按钮功能实现：
e.p:
```Java
ImageButton userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(userIntent);
            }
        });
```

### 拍照功能调用：
在AndroidManifest.xml文件中配置如下权限：  
```xml
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

Java代码中拍照功能以及读取相册功能实现：  
```Java
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
```

### 识别功能实现
Chaquopy 是一个用于在 Android Studio 中嵌入 Python 代码的插件。我们通过ChaquoPy方法调用我们的python方法。  
在gradle中配置：  
```gradle
plugins {
    id 'com.chaquo.python'
}

chaquopy {
    pythonVersion "3.8"  // 选择所需的 Python 版本
    ndkVersion "21.4.7075529"  // 选择所需的 NDK 版本
}
```
在文件中调用方法：  
```Java
 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Python python = Python.getInstance();
        PyObject pyObject = python.getModule(imagePath);
        String result = pyObject.callAttr(imagePath).toString();

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
```
