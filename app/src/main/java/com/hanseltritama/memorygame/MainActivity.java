package com.hanseltritama.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int[][] data = new int[][] {
            { R.drawable.puppy, R.raw.dog },
            { R.drawable.kitten, R.raw.cat },
            { R.drawable.monkey, R.raw.monkey },
            { R.drawable.pig, R.raw.pig }
    };
    private Card[] cards = new Card[data.length * 2];
    private int matchedCounter = 0;
    private int clickCounter = 0;
    private int[] clickedCard = new int[2];

    public <T> void shuffle(T[] cards) {
        Random rand = new Random();
        for(int i = cards.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            swap(i, index, cards);
        }
    }

    public <T> void swap(int a, int b, T[] cards) {
        T temp = cards[a];
        cards[a] = cards[b];
        cards[b] = temp;
    }

    public void resetImage() {
        ImageButton first_card = findViewById(getResources()
                .getIdentifier("imageButton" + String.valueOf(clickedCard[0]),"id", getPackageName()));
        ImageButton second_card = findViewById(getResources()
                .getIdentifier("imageButton" + String.valueOf(clickedCard[1]),"id", getPackageName()));
        first_card.setImageResource(R.drawable.question_mark);
        second_card.setImageResource(R.drawable.question_mark);
    }

    public void resetClickedCardArray() {
        Arrays.fill(clickedCard, 0);
    }

    public void resetClickCounter() {
        clickCounter = 0;
    }

    public boolean checkMatch() {
        return (cards[clickedCard[0]].imageFile == cards[clickedCard[1]].imageFile);
    }

    public void onImageClick(View view) {
        ImageButton imageButton = (ImageButton) view;
        int imgId = Integer.parseInt(imageButton.getTag().toString());
        imageButton.setImageResource(cards[imgId].imageFile);
        clickedCard[clickCounter] = imgId;
        clickCounter++;
        if(clickCounter == 2) {
            if(!checkMatch()) {
                resetImage();
                resetClickedCardArray();
                resetClickCounter();
            }
            else {
                resetClickedCardArray();
                resetClickCounter();
                matchedCounter++;
                if(matchedCounter == data[0].length) {
                    Toast.makeText(this, "WIN!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < data.length; i++) {
            cards[i] = new Card(data[i][0], data[i][1]);
        }
        for(int i = 0; i < data.length; i++) {
            cards[i + 4] = new Card(data[i][0], data[i][1]);
        }

        shuffle(cards);

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

//public int countButton() {
//        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
//        int childCount = gridLayout.getChildCount();
//        int buttonCount = 0;
//        for(int i = 0; i < childCount; i++) {
//            if(gridLayout.getChildAt(i) instanceof ImageButton) {
//                buttonCount++;
//            }
//        }
//        return buttonCount;
//    }
//
//        int btnCount = countButton();
//        for(int i = 0; i < cards.length; i++) {
//        ImageButton imageButton = findViewById(getResources()
//        .getIdentifier("imageButton" + String.valueOf(i),"id", getPackageName()));
//        imageButton.setImageResource(cards[i].imageFile);
//        }