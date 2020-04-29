package com.example.cryptography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class CaeserCipher extends AppCompatActivity {

    EditText msg,key;
    Button btnencrypt,btndecrypt;
    TextView tvresult,tvresult2;
    ImageView copy1,copy2;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caeser_cipher);

        msg = findViewById(R.id.msg);
        key = findViewById(R.id.key);
        tvresult = findViewById(R.id.tvresult);
        tvresult2 = findViewById(R.id.tvresult2);
        btnencrypt = findViewById(R.id.btnencrypt);
        btndecrypt = findViewById(R.id.btndecrypt);
        //progressBar = findViewById(R.id.progressBar);
        copy1 = findViewById(R.id.copy1);
        copy2 = findViewById(R.id.copy2);

        btnencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //progressBar.setVisibility(View.VISIBLE);

                String plainText = msg.getText().toString().trim();
                //String key1 = key.getText().toString().trim();
                int Key = Integer.parseInt( key.getText().toString().trim() );

                // Creates a new text clip to put on the clipboard


                if(plainText.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both Fields are Required",Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.INVISIBLE);

                }
                if (key == null)
                {
                    Toast.makeText(getApplicationContext(),"Both Fields are Required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Encryption_CaeserCipher(plainText,Key);
                }



            }

            public void Encryption_CaeserCipher(String plainText, int key) {

                StringBuffer cipher_text = new StringBuffer("");
                String Alphabets1[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
                List Alphabets = Arrays.asList(new Character[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'});

                plainText.toLowerCase();
                plainText = plainText.replaceAll("\\s", "");
                char[] sentence = plainText.toCharArray();


                for (int i = 0;i < sentence.length;i++)
                {
                    if (Alphabets.indexOf(sentence[i]) + key > 25)
                    {
                        int j = 0;
                        int x =  key - (25 - Alphabets.indexOf(sentence[i])) - 1;
                        if (x == -1 || x==0)
                        {
                            cipher_text.append(Alphabets1[j]);
                        }
                        else
                        {
                            cipher_text.append(Alphabets1[j + x]);
                        }

                    }
                    else
                    {
                        cipher_text.append(Alphabets1[Alphabets.indexOf(sentence[i]) + key]);
                    }

                }
                //System.out.println(cipher_text);
                tvresult.setText("Your cipher text=" + " " + cipher_text);
                //copy2.setVisibility(View.INVISIBLE);
                copy1.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.INVISIBLE);

            }
            });


        btndecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cipher_text = msg.getText().toString().trim();
                String key1 = key.getText().toString().trim();
                int Key = Integer.parseInt( key.getText().toString() );

                //progressBar.setVisibility(View.VISIBLE);
                if(cipher_text.isEmpty() || key1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both Fields are Required",Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.INVISIBLE);

                }
                else
                {
                    Decryption_CaeserCipher(cipher_text,Key);
                }
            }

            public void Decryption_CaeserCipher(String cipher_text, int key) {

                StringBuffer plain_text = new StringBuffer("");
                String Alphabets1[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
                List Alphabets = Arrays.asList(new Character[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'});

                cipher_text.toLowerCase();
                cipher_text = cipher_text.replaceAll("\\s", "");
                //System.out.println(plain_text);
                char[] sentence = cipher_text.toCharArray();

                //System.out.println(Alphabets.indexOf(sentence[2]));

                for (int i = 0;i < sentence.length;i++)
                {
                    if (Alphabets.indexOf(sentence[i]) - key < 0)
                    {
                        int j = 25;
                        int x =  key - 1-Alphabets.indexOf(sentence[i]) ;

                        plain_text.append(Alphabets1[j - x]);
                /*if (x == -1 || x==0)
                {
                    cipher_text.append(Alphabets1[j]);
                }
                else
                {
                    cipher_text.append(Alphabets1[j + x]);
                }*/

                    }
                    else
                    {
                        plain_text.append(Alphabets1[Alphabets.indexOf(sentence[i]) - key]);
                    }

                }
                tvresult2.setText("plainText ="+" "+plain_text);
                //copy1.setVisibility(View.INVISIBLE);
                copy2.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });
        }

    public void copy1(View view)
    {
        String text1 = tvresult.getText().toString().trim();
        String[] text2 = text1.split("=",2);
        String text3 = text2[1];
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text",text3);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_SHORT).show();
    }

    public void copy2(View view)
    {
        String text1 = tvresult2.getText().toString().trim();
        String[] text2 = text1.split("=",2);
        String text3 = text2[1];
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text",text3);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_SHORT).show();
    }
}
