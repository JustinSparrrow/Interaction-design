package com.moqi.buggames.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.moqi.buggames.R;
import com.moqi.buggames.dictionary.AnimalNameManager;
import com.moqi.buggames.dictionary.CardView;

public class DictionaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_activity);

        // 获取之前保存的动物名称
        String savedAnimalName = AnimalNameManager.getAnimalName(this);

        // 设置CardView的属性
        setCardViewAttributes(R.id.cardView1, R.drawable.cangying, "苍蝇", "1", savedAnimalName);
        setCardViewAttributes(R.id.cardView2, R.drawable.wenzi, "蚊子", "2", savedAnimalName);
        setCardViewAttributes(R.id.cardView3, R.drawable.mifeng, "蜜蜂", "3", savedAnimalName);
        setCardViewAttributes(R.id.cardView4, R.drawable.huangfeng, "帝王蜂", "4", savedAnimalName);
        setCardViewAttributes(R.id.cardView5, R.drawable.qingting, "蜻蜓", "5", savedAnimalName);

        // 更新CardView的显示
        updateCardViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCardViews();
    }

    private void updateCardViews() {
        // 获取之前保存的动物名称
        String savedAnimalName = AnimalNameManager.getAnimalName(this);

        // 检查每个CardView是否包含保存的动物名称
        for (int i = 1; i <= 5; i++) {
            // 获取对应的CardView
            CardView cardView = findViewById(getResources().getIdentifier("cardView" + i, "id", getPackageName()));
            if (cardView != null) {
                // 如果找到匹配项，更新CardView的显示
                if (savedAnimalName != null && cardView.getNameText().contains(savedAnimalName)) {
                    cardView.hideMask();  // 不可见
                    cardView.setNameText(savedAnimalName);  // 更新名称
                    cardView.setLevelText("Updated Level");  // 更新等级，你可以根据需要设置具体的等级
                }
            }
        }
    }

    // 设置CardView的属性
    private void setCardViewAttributes(int cardViewId, int pictureResId, String name, String level, String savedAnimalName) {
        CardView cardView = findViewById(cardViewId);
        if (cardView != null) {
            cardView.setPicture(pictureResId);
            cardView.showMask();
            cardView.setNameText(name);
            cardView.setLevelText(level);
        }
    }
}
