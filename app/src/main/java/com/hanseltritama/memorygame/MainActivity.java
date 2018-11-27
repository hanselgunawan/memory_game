package com.hanseltritama.memorygame;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageView oopsImageView;

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
        Arrays.fill(clickedCard, -1);
    }

    public void resetClickCounter() {
        clickCounter = 0;
    }

    public boolean checkMatch() {
        return (cards[clickedCard[0]].imageFile == cards[clickedCard[1]].imageFile);
    }

    public void playSound() {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, cards[clickedCard[clickCounter]].soundFile);
        mediaPlayer.start();
    }

    public void disableAllButtons() {
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            view.setEnabled(false);
            view.setAlpha(0.5f);
        }
    }

    public void disableMatchedButton() {
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        View first_button = gridLayout.getChildAt(clickedCard[0]);
        View second_button = gridLayout.getChildAt(clickedCard[1]);
        first_button.setEnabled(false);
        second_button.setEnabled(false);
    }

    public void enableButtons() {
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            view.setEnabled(true);
            view.setAlpha(1f);
        }
    }

    public void displayTryAgainMessage() {
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        View try_again_layout = findViewById(R.id.try_again_layout);
        gridLayout.setAlpha(0.5f);
        gridLayout.setEnabled(false);
        try_again_layout.setVisibility(View.VISIBLE);
    }

    public void onImageClick(View view) {
        ImageButton imageButton = (ImageButton) view;
        int imgId = Integer.parseInt(imageButton.getTag().toString());

        if(clickedCard[0] == imgId) return;

        imageButton.setImageResource(cards[imgId].imageFile);
        clickedCard[clickCounter] = imgId;
        playSound();

        clickCounter++;

        if(clickCounter == 2) {
            if(!checkMatch()) {
                disableAllButtons();
                oopsImageView.setVisibility(View.VISIBLE);
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        resetImage();
                        resetClickedCardArray();
                        resetClickCounter();
                        enableButtons();
                        oopsImageView.setVisibility(View.GONE);
                    }
                };
                new Timer().schedule(new TimerTask() {//Timer is on a different Thread with UI updates
                    @Override
                    public void run() {
                        runOnUiThread(runnable);//passing action to Main Thread Looper's queue
                    }
                }, 1000);
            }
            else {
                disableMatchedButton();
                resetClickedCardArray();
                resetClickCounter();
                matchedCounter++;
                if(matchedCounter == data.length) {
                    displayTryAgainMessage();
                }
            }
        }
    }

    public void onClickYes(View view) {
        recreate();
    }

    public void onClickNo(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Arrays.fill(clickedCard, -1);
        oopsImageView = findViewById(R.id.oopsImageView);
        oopsImageView.setVisibility(View.GONE);

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