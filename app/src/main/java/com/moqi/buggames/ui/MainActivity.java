package com.moqi.buggames.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.moqi.buggames.R;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(userIntent);
            }
        });

        ImageButton identifyButton = findViewById(R.id.identifyButton);
        identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent identifyIntent = new Intent(MainActivity.this, IdentifyActivity.class);
                startActivity(identifyIntent);
            }
        });

        findViewById(R.id.gameButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
            startActivity(intent);
        });


        ImageButton dictionaryButton = findViewById(R.id.dictionaryButton);
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dictionaryIntent = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(dictionaryIntent);
            }
        });

    }
}
