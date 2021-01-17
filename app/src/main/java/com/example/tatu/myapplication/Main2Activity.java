package com.example.tatu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void Kirish(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void Dastur(View view) {
        startActivity(new Intent(this, Main3Activity.class));
    }

    public void Chiqish2(View view) {
        finish();
    }

    public void Record(View view) {
        startActivity(new Intent(this, Record.class));
    }
}
