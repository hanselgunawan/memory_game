package com.hanseltritama.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public <T> void shuffle(T[] cards) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[][] data = new int[][] {
            { R.drawable.puppy, R.raw.dog },
            { R.drawable.kitten, R.raw.cat },
            { R.drawable.monkey, R.raw.monkey },
            { R.drawable.pig, R.raw.pig }
        };

        Card[] cards = new Card[data.length * 2];
        for(int i = 0; i < data.length; i++) {
            cards[i] = new Card(data[i][0], data[i][1]);
        }
        for(int i = 0; i < data.length; i++) {
            cards[i + 4] = new Card(data[i][0], data[i][1]);
        }
    }

    private class Card {
        int imageFile;
        int soundFile;

        public Card(int imageFile, int soundFile) {
            this.imageFile = imageFile;
            this.soundFile = soundFile;
        }
    }
}