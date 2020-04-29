package com.example.cryptography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnCaeserCipher,btnPlayFsirCipher,btnDEScipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaeserCipher = findViewById(R.id.btnCaeserCipher);
        btnPlayFsirCipher = findViewById(R.id.btnPlayFairCipher);
        btnDEScipher = findViewById(R.id.btnDEScipher);

        btnCaeserCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caesercipher = new Intent(MainActivity.this,CaeserCipher.class);
                startActivity(caesercipher);
            }
        });
        btnPlayFsirCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playfaircipher = new Intent(MainActivity.this,PlayFairCipher.class);
                startActivity(playfaircipher);
            }
        });
        btnDEScipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Descipher = new Intent(MainActivity.this,DES.class);
                startActivity(Descipher);
            }
        });
    }
}
