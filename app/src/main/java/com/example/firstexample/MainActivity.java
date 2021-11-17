package com.example.firstexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int turn, turnCounter;
    boolean[][] isX,isO;
    ImageButton[][] imageButtons;
    TextView turnDecider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button resetbtn = findViewById(R.id.main_reset_btn);
        turnDecider = findViewById(R.id.main_text_tv);
        imageButtons = new ImageButton[3][3];
        isX = new boolean[3][3];
        isO = new boolean[3][3];
        turn = 0; //0-> X turn to play , 1-> O turn to play, 2->game over (some one won or draw)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                isX[i][j] = false;
                isO[i][j] = false;
                String name = "main_" + i + j + "_ibtn";
                int id = getResources().getIdentifier(name, "id", getPackageName());
                imageButtons[i][j] = findViewById(id);
                imageButtons[i][j].setImageResource(R.drawable.firstpic);
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
                                turnDecider.setText("X WINS :)");
                                turn = 2; //do not allow any more presses
                            } else {
                                turn = 1; // 'O' turn
                                turnDecider.setText("O Play Next");
                            }
                        } else if (turn == 1 && !isO[row][column] && !isX[row][column]) {//O->turn to play and ibtn wasn't pressed before
                            turnCounter++;
                            imageButtons[row][column].setImageResource(R.drawable.o);//change pic to 'O'
                            isO[row][column] = true;
                            if (checkForWin(row,column,isO)) {
                                turnDecider.setText("O WINS :)");
                                turn = 2;//do not allow any more presses
                            } else {
                                turn = 0;//'X' turn
                                turnDecider.setText("X Play Next");
                            }
                        }
                        if (turn != 2 && turnCounter == 9) {
                            turnDecider.setText("It's A Draw");
                            turn = 2;
                        }
                    }
                });
            }
        }
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        imageButtons[i][j].setImageResource(R.drawable.firstpic);
                        if (isX[i][j] == true) {
                            isX[i][j] = false;
                        } else if (isO[i][j] == true) {
                            isO[i][j] = false;
                        }
                    }
                }
                turnDecider.setText("X Play First");
                turn = 0;
                turnCounter = 0;
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
        return win;
    }
    public boolean checkColumn(int column, boolean[][] currentState) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (!currentState[i][column]){break;}
            if (i == 2){win = true;}
        }
        return win;
    }
    public boolean checkDiagonal(boolean[][] currentState) {
        boolean win = false;
        if (currentState[1][1] &&
                ( (currentState[0][0] && currentState[2][2]) || (currentState[0][2] && currentState[2][0]) )) {
            win = true;
        }
        return win;
    }
}