package com.moqi.buggames.dictionary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moqi.buggames.R;

import java.util.Collection;

public class CardView extends RelativeLayout {
    private ImageView picture;
    private View mask;
    private TextView name;
    private TextView level;

    public CardView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dictionary_block, this, true);

        picture = findViewById(R.id.picture);
        mask = findViewById(R.id.mask);
        name = findViewById(R.id.name);
        level = findViewById(R.id.level);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CardView);
        int pictureResId = attributes.getResourceId(R.styleable.CardView_app_srcCompat, R.drawable.qixingpiaochong);
        String nameText = attributes.getString(R.styleable.CardView_app_nameText);
        String levelText = attributes.getString(R.styleable.CardView_app_levelText);
        attributes.recycle();

        setPicture(pictureResId);
        setNameText(nameText != null ? nameText : "未知");
        setLevelText(levelText != null ? levelText : "暂无");
    }

    public void setPicture(int resourceId){
        picture.setImageResource(resourceId);
    }

    public void showMask(){
        mask.setVisibility(View.VISIBLE);
    }

    public void hideMask(){
        mask.setVisibility(View.GONE);
    }

    public void setNameText(String text){
        text = "名称："+text;
        name.setText(text);
    }

    public void setLevelText(String text){
        text = "等级："+text;
        level.setText(text);
    }

    public String getNameText() {
        return name.getText().toString();
    }
}
