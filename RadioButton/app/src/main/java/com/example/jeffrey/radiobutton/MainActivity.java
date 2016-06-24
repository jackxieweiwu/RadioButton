package com.example.jeffrey.radiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jeffrey.radiobutton.view.NumberRadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberRadioButton num = (NumberRadioButton) findViewById(R.id.num);
        num.setNum(13);

        NumberRadioButton num2 = (NumberRadioButton) findViewById(R.id.num2);
        num2.setNum(3);
        num2.setDebug(true);
    }
}
