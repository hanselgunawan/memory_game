package com.hanseltritama.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[][] cards = new String[][] {

        };
    }

    private class Card {
        String imageFile;
        String soundFile;

        public Card(String imageFile, String soundFile) {
            this.imageFile = imageFile;
            this.soundFile = soundFile;
        }
    }
}