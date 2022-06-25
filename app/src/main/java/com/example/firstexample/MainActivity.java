package com.example.firstexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int turn, turnCounter;
    boolean[][] isX,isO;
    ImageButton[][] imageButtons;
    ImageView turnDecider;
    ImageView winIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Tic Tac Toe");
        Button resetBtn = findViewById(R.id.main_reset_btn);
        turnDecider = findViewById(R.id.main_title_iv);
        turnDecider.setImageResource(R.drawable.xplay);
        imageButtons = new ImageButton[3][3];
        isX = new boolean[3][3];
        isO = new boolean[3][3];
        winIv = findViewById(R.id.main_win0_iv);
        turn = 0; //0-> X turn to play , 1-> O turn to play, 2->game over (some one won or draw)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                isX[i][j] = false;
                isO[i][j] = false;
                String name = "main_" + i + j + "_ibtn";
                int id = getResources().getIdentifier(name, "id", getPackageName());
                imageButtons[i][j] = findViewById(id);
                imageButtons[i][j].setBackgroundDrawable(null);
                imageButtons[i][j].setImageResource(R.drawable.empty);
                int row = i;
                int column = j;
                imageButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (turn == 0 && !isX[row][column] && !isO[row][column]) {//X->turn to play and ibtn wasn't pressed before
                            turnCounter++;
                            imageButtons[row][column].setImageResource(R.drawable.x);//change pic to 'X'
                            isX[row][column] = true;
                            if (checkForWin(row,column,isX)) {
                                turnDecider.setImageResource(R.drawable.xwin);
                                turn = 2; //do not allow any more presses
                            } else {
                                turn = 1; // 'O' turn
                                turnDecider.setImageResource(R.drawable.oplay);
                            }
                        } else if (turn == 1 && !isO[row][column] && !isX[row][column]) {//O->turn to play and ibtn wasn't pressed before
                            turnCounter++;
                            imageButtons[row][column].setImageResource(R.drawable.o);//change pic to 'O'
                            isO[row][column] = true;
                            if (checkForWin(row,column,isO)) {
                                turnDecider.setImageResource(R.drawable.owin);
                                turn = 2;//do not allow any more presses
                            } else {
                                turn = 0;//'X' turn
                                turnDecider.setImageResource(R.drawable.xplay);
                            }
                        }
                        if (turn != 2 && turnCounter == 9) {
                            turnDecider.setImageResource(R.drawable.nowin);
                            turn = 2;
                        }
                    }
                });
            }

        }
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        imageButtons[i][j].setImageResource(R.drawable.empty);
                        if (isX[i][j]) {
                            isX[i][j] = false;
                        } else if (isO[i][j]) {
                            isO[i][j] = false;
                        }
                    }
                }
                turnDecider.setImageResource(R.drawable.xplay);
                turn = 0;
                turnCounter = 0;
                winIv.setImageResource(R.drawable.empty);
            }
        });
    }
    public boolean checkForWin(int row, int column, boolean[][] currentState) {
        boolean win = false;
        if(turnCounter>4) {
            win = checkRow(row, currentState);
            if (!win){win = checkColumn(column, currentState);}
            if (!win){win = checkDiagonal(currentState);}
        }
        return win;
    }
    public boolean checkRow(int row, boolean[][] currentState) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (!currentState[row][i]){break;}
            if (i == 2){win = true;}
        }
        if(win==true) {
            if (row == 0) {
                winIv.setImageResource(R.drawable.mark6);
            } else if (row == 1) {
                winIv.setImageResource(R.drawable.mark7);
            } else {
                winIv.setImageResource(R.drawable.mark8);
            }
        }
        return win;
    }
    public boolean checkColumn(int column, boolean[][] currentState) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (!currentState[i][column]){break;}
            if (i == 2){win = true;}
        }
        if(win==true) {
            if (column == 0) {
                winIv.setImageResource(R.drawable.mark3);
            } else if (column == 1) {
                winIv.setImageResource(R.drawable.mark4);
            } else {
                winIv.setImageResource(R.drawable.mark5);
            }
        }
        return win;
    }
    public boolean checkDiagonal(boolean[][] currentState) {
        boolean win = false;
        if (currentState[1][1]){
            if( currentState[0][0] && currentState[2][2] ){
                winIv.setImageResource(R.drawable.mark1);
                win=true;
            }
            else if( currentState[0][2] && currentState[2][0] ) {
                winIv.setImageResource(R.drawable.mark2);
                win = true;
            }
        }
        return win;
    }
}