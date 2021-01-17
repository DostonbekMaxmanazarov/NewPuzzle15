package com.example.tatu.myapplication;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private int count;
    private Button[][] buttons;
    private Koordinate boshJoy;
    private TextView textcount;
    private TextView textScoreid;
    private TextView textTimeid;
    private ViewGroup visiblitiy;
    private Chronometer chronometer;
    private TextView count1,count2,count3;
    private long base;
    private ArrayList<String> text;
    private ArrayList<Integer> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadButtons();
        loadData();
        loadDataToView();
        setTitle("Puzzle 15");
    }

    private void loadButtons() {
        count1=findViewById(R.id.count1);
        count2=findViewById(R.id.count2);
        count3=findViewById(R.id.count3);
        ViewGroup viewGroup = findViewById(R.id.group);
        textScoreid = findViewById(R.id.textScoreid);
        textcount = findViewById(R.id.textcount);
        chronometer = findViewById(R.id.textTime);
        visiblitiy = findViewById(R.id.visiblitiy);
        textTimeid = findViewById(R.id.textTimeid);
        buttons = new Button[4][4];
        int soniB = viewGroup.getChildCount();
        for (int i = 0; i < soniB; i++) {
            int x = i / 4;
            int y = i % 4;
            //buttons[i/4][i%4]=(Button) viewGroup.getChildAt(i);
            Button b = (Button) viewGroup.getChildAt(i);
            buttons[x][y] = b;
            buttons[x][y].setOnClickListener(this::click);
            buttons[x][y].setTag(new Koordinate(x, y));
        }
        boshJoy = new Koordinate(3, 3);
    }

    private void loadData() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
    }


    @SuppressLint("SetTextI18n")
    private void loadDataToView() {

        count = 0;
        base = SystemClock.elapsedRealtime();
        chronometer.setBase(base);
        chronometer.start();
        textcount.setText("" + (count));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i * 4 + j < 15) buttons[i][j].setText(numbers.get(i * 4 + j) + "");
            }
        }

        buttons[boshJoy.getX()][boshJoy.getY()].setBackgroundResource(R.drawable.bg_button3);
        buttons[3][3].setText("");
        buttons[3][3].setBackgroundResource(R.drawable.bg_button1);
        boshJoy = (Koordinate) buttons[3][3].getTag();

    }

    @SuppressLint("SetTextI18n")
    public void click(View view) {

        Button button = (Button) view;
        Koordinate koordinate = (Koordinate) button.getTag();

        int dx = Math.abs(boshJoy.getX() - koordinate.getX());
        int dy = Math.abs(boshJoy.getY() - koordinate.getY());

        if (dx == 0 && dy == 1 || dx == 1 && dy == 0) {
            textcount.setText("" + (++count));
            int t = count;
            buttons[boshJoy.getX()][boshJoy.getY()].setText(button.getText());
            buttons[boshJoy.getX()][boshJoy.getY()].setBackgroundResource(R.drawable.bg_button3);
            button.setText("");
            button.setBackgroundResource(R.drawable.bg_button1);
            boshJoy = koordinate;
            if (isYutuq()) {
                if (count==0){
                    count1.setText("");
                    count2.setText("");
                    count3.setText("");
                }if (count>0){
                    count1.setText(count+"");
                }
                visiblitiy.setVisibility(View.VISIBLE);
                chronometer.stop();
                textScoreid.setText("" + t);
                textTimeid.setText(chronometer.getText());

            }
        }
    }

    public boolean isYutuq() {

        if (boshJoy.getX() != 3 && boshJoy.getY() != 3) return false;

        boolean b = true;
        for (int i = 0; i < 15; i++) {
            b &= buttons[i / 4][i % 4].getText().equals(String.valueOf(i + 1));
        }
        return b;
    }

    public void Restart(View view) {
        loadData();
        loadDataToView();
    }

    public void Finish(View view) {
        finish();
    }

    public void Chiqish(View view) {
        finish();
    }

    public void Next(View view) {
        Restart(view);
        visiblitiy.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        text = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            String add = (String) buttons[i / 4][i % 4].getText();
            text.add(add);
        }
        outState.putStringArrayList("Text", text);
        outState.putInt("Score", count);
        outState.putLong("Time", base);
        outState.putInt("Koorx", boshJoy.getX());
        outState.putInt("Koory", boshJoy.getY());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("Score");
            textcount.setText(String.valueOf(count));
            base = savedInstanceState.getLong("Time");
            chronometer.setBase(base);
            chronometer.start();
            boshJoy = (Koordinate) buttons[savedInstanceState.getInt("Koorx")][savedInstanceState.getInt("Koory")].getTag();
            buttons[3][3].setBackgroundResource(R.drawable.bg_button3);
            buttons[boshJoy.getX()][boshJoy.getY()].setBackgroundResource(R.drawable.bg_button1);
            buttons[boshJoy.getX()][boshJoy.getY()].setText("");

            text = savedInstanceState.getStringArrayList("Text");
            for (int i = 0; i < 16; i++) {
                buttons[i / 4][i % 4].setText(text.get(i));
            }
        }
    }

    public void Pause(View view) {
    }
}