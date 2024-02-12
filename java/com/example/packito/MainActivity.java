package com.example.packito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton buttonHol;
    private ImageButton buttonMove;
    private ImageButton buttonStud;
    private ImageButton buttonSleep;
    private ImageButton buttonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonHol = findViewById(R.id.holidaysbutton);
        buttonMove = findViewById(R.id.movebutton);
        buttonStud = findViewById(R.id.studentbutton);
        buttonSleep = findViewById(R.id.sleepoverImage);
        buttonInfo = findViewById(R.id.info);

        buttonHol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Holidays.class);
                startActivity(intent);
            }
        });

        buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Move.class);
                startActivity(intent);
            }
        });

        buttonStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Student.class);
                startActivity(intent);
            }
        });

        buttonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sleepover.class);
                startActivity(intent);
            }
        });

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Info.class);
                startActivity(intent);
            }
        });
    }
}