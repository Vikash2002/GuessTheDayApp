package com.example.guesstheday;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StartActivity extends AppCompatActivity {

    public RadioGroup Options;
    public TextView Ques,Points;
    public RadioButton optionA,optionB,optionC,optionD,checker;
    public static String Marks;
    public Vibrator v;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Options = findViewById(R.id.options);
        Ques = findViewById(R.id.question);
        Points = findViewById(R.id.points);
        optionA = findViewById(R.id.radioButton1);
        optionB = findViewById(R.id.radioButton2);
        optionC = findViewById(R.id.radioButton3);
        optionD = findViewById(R.id.radioButton4);
        Answers.a=0;
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        generate();

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });
        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });
        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });
        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void generate()
    {
        int y = ThreadLocalRandom.current().nextInt(1900,2021);
        int m = ThreadLocalRandom.current().nextInt(1,12);
        int d;
        if (m == 2) {
            if (leapyear(y) == 1) {
                d = ThreadLocalRandom.current().nextInt(1, 29);
            } else {
                d = ThreadLocalRandom.current().nextInt(1, 28);
            }
        } else {
            if ((m % 2 == 0 && m < 8) || (m % 2 != 0 && m > 8))
                d = ThreadLocalRandom.current().nextInt(1, 30);
            else
                d = ThreadLocalRandom.current().nextInt(1, 31);
        }
        Ques.setText(d+"-"+m+"-"+y );
        Calendar c = Calendar.getInstance();
        c.set(y,m - 1, d);
        int x = c.get(Calendar.DAY_OF_WEEK);
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday"};
        String ans = days[x - 1];
        Answers.ans=ans;
        shuffle(days);
        String[] opt = {days[0],days[1],days[2],days[3]};
        evaluate(opt,ans);
        optionA.setText(opt[0]);
        optionB.setText(opt[1]);
        optionC.setText(opt[2]);
        optionD.setText(opt[3]);
        Options.clearCheck();
    }
    public int leapyear(int x) {
        if (x % 400 == 0) {
            return 1;
        }
        if (x % 100 == 0)
            return 0;
        if (x % 4 == 0)
            return 1;
        else
            return 0;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void evaluate(String[] opt, String ans)
    {
        int y=0;
        for(int i=0;i<4;i++)
        {
            if(ans==opt[i])
            {
                y++;
            }
        }
        if(y==0)
            opt[0]=ans;
        shuffle(opt);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void choose()
    {
        int RadioId = Options.getCheckedRadioButtonId();
        checker = findViewById(RadioId);
        if(checker.getText()==Answers.ans)
        {
            Answers.a++;
            Points.setText("Points : "+Answers.a);
            v.vibrate(300);
            generate();
        }
        else {
            v.vibrate(1000);
            finishAffinity();
            End();
        }
    }
    public void End()
    {
        Intent intent = new Intent(this,Result.class);
        String s= String.valueOf(Answers.a);
        intent.putExtra(Marks,s);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void shuffle(String[] shuf)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = shuf.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String sh = shuf[index];
            shuf[index] = shuf[i];
            shuf[i] = sh;
        }
    }
}