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
