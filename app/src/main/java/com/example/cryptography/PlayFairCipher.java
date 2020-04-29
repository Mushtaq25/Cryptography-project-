package com.example.cryptography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;

public class PlayFairCipher extends AppCompatActivity {

    EditText msg,key;
    Button btnencrypt,btndecrypt;
    TextView tvresult,tvresult2;
    //ProgressBar progressBar;
    ImageView copy1,copy2;
    private static char[][] charTable;
    private static Point[] positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_fair_cipher);

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
                String Key = key.getText().toString().trim();
                if(plainText.isEmpty() || Key.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both Fields are Required",Toast.LENGTH_SHORT).show();
                   // progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    PFEncryption pfEncryption=new PFEncryption();
                    pfEncryption.makeArray(Key);
                    String msg=pfEncryption.manageMessage(plainText);
                    pfEncryption.doPlayFair(msg, "Encrypt");
                    String en=pfEncryption.getEncrypted();
                    tvresult.setText("Your cipher text="+" " + en);
                    copy1.setVisibility(View.VISIBLE);
                    //progressBar.setVisibility(View.INVISIBLE);
                }
            }

            class PFEncryption{

                private char [][] alphabets= new char[5][5];
                private char[] uniqueChar= new char[26];
                private String ch="ABCDEFGHIKLMNOPQRSTUVWXYZ";
                private String encrypted="";
                private String decrypted="";

                void makeArray(String keyword){
                    keyword=keyword.toUpperCase().replace("J","I");
                    boolean present, terminate=false;
                    int val=0;
                    int uniqueLen;
                    for (int i=0; i<keyword.length(); i++){
                        present=false;
                        uniqueLen=0;
                        if (keyword.charAt(i)!= ' '){
                            for (int k=0; k<uniqueChar.length; k++){
                                if (Character.toString(uniqueChar[k])==null){
                                    break;
                                }
                                uniqueLen++;
                            }
                            for (int j=0; j<uniqueChar.length; j++){
                                if (keyword.charAt(i)==uniqueChar[j]){
                                    present=true;
                                }
                            }
                            if (!present){
                                uniqueChar[val]=keyword.charAt(i);
                                val++;
                            }
                        }
                        ch=ch.replaceAll(Character.toString(keyword.charAt(i)), "");
                    }

                    for (int i=0; i<ch.length(); i++){
                        uniqueChar[val]=ch.charAt(i);
                        val++;
                    }
                    val=0;

                    for (int i=0; i<5; i++){
                        for (int j=0; j<5; j++){
                            alphabets[i][j]=uniqueChar[val];
                            val++;
                            //System.out.print(alphabets[i][j] + "\t");
                        }
                        //System.out.println();
                    }
                }

                String manageMessage(String msg){
                    int val=0;
                    int len=msg.length()-2;
                    String newTxt="";
                    String intermediate="";
                    while (len>=0){
                        intermediate=msg.substring(val, val+2);
                        if (intermediate.charAt(0)==intermediate.charAt(1)){
                            newTxt=intermediate.charAt(0) + "x" + intermediate.charAt(1);
                            msg=msg.replaceFirst(intermediate, newTxt);
                            len++;
                        }
                        len-=2;
                        val+=2;
                    }

                    if (msg.length()%2!=0){
                        msg=msg+'x';
                    }
                    return msg.toUpperCase().replaceAll("J","I").replaceAll(" ","");
                }

                void doPlayFair(String msg, String tag){
                    int val=0;
                    while (val<msg.length()){
                        searchAndEncryptOrDecrypt(msg.substring(val, val + 2), tag);
                        val+=2;
                    }
                }

                void searchAndEncryptOrDecrypt(String doubblyCh, String tag){
                    char ch1=doubblyCh.charAt(0);
                    char ch2=doubblyCh.charAt(1);
                    int row1=0, col1=0, row2=0, col2=0;
                    for (int i=0; i<5; i++){
                        for (int j=0; j<5; j++){
                            if (alphabets[i][j]==ch1){
                                row1=i;
                                col1=j;
                            }else if (alphabets[i][j]==ch2){
                                row2=i;
                                col2=j;
                            }
                        }
                    }
                    if (tag=="Encrypt")
                        encrypt(row1, col1, row2, col2);
                    else if(tag=="Decrypt")
                        decrypt(row1, col1, row2, col2);
                }

                void encrypt(int row1, int col1, int row2, int col2){
                    if (row1==row2){
                        col1=col1+1;
                        col2=col2+1;
                        if (col1>4)
                            col1=0;
                        if (col2>4)
                            col2=0;
                        encrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row1][col2]));
                    }else if(col1==col2){
                        row1=row1+1;
                        row2=row2+1;
                        if (row1>4)
                            row1=0;
                        if (row2>4)
                            row2=0;
                        encrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row2][col1]));
                    }else{
                        encrypted+=(Character.toString(alphabets[row1][col2])+Character.toString(alphabets[row2][col1]));
                    }
                }

                void decrypt(int row1, int col1, int row2, int col2){
                    if (row1==row2){
                        col1=col1-1;
                        col2=col2-1;
                        if (col1<0)
                            col1=4;
                        if (col2<0)
                            col2=4;
                        decrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row1][col2]));
                    }else if(col1==col2){
                        row1=row1-1;
                        row2=row2-1;
                        if (row1<0)
                            row1=4;
                        if (row2<0)
                            row2=4;
                        decrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row2][col1]));
                    }else{
                        decrypted+=(Character.toString(alphabets[row1][col2])+Character.toString(alphabets[row2][col1]));
                    }
                }

                String getEncrypted(){
                    return encrypted;
                }
                String getDecrypted(){
                    return decrypted;
                }



            }

        });
        btndecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                String plainText = msg.getText().toString().trim();
                String Key = key.getText().toString().trim();

                if(plainText.isEmpty() || Key.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both Fields are Required",Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    PFEncryption pfEncryption=new PFEncryption();
                    pfEncryption.makeArray(Key);
                    String msg=pfEncryption.manageMessage(plainText);
                    pfEncryption.doPlayFair(msg, "Decrypt");
                    String dec = pfEncryption.getDecrypted();
                    tvresult2.setText("Your plain text="+" " + dec);
                    copy2.setVisibility(View.VISIBLE);
                    //progressBar.setVisibility(View.INVISIBLE);
                }

            }

            class PFEncryption{

                private char [][] alphabets= new char[5][5];
                private char[] uniqueChar= new char[26];
                private String ch="ABCDEFGHIKLMNOPQRSTUVWXYZ";
                private String encrypted="";
                private String decrypted="";

                void makeArray(String keyword){
                    keyword=keyword.toUpperCase().replace("J","I");
                    boolean present, terminate=false;
                    int val=0;
                    int uniqueLen;
                    for (int i=0; i<keyword.length(); i++){
                        present=false;
                        uniqueLen=0;
                        if (keyword.charAt(i)!= ' '){
                            for (int k=0; k<uniqueChar.length; k++){
                                if (Character.toString(uniqueChar[k])==null){
                                    break;
                                }
                                uniqueLen++;
                            }
                            for (int j=0; j<uniqueChar.length; j++){
                                if (keyword.charAt(i)==uniqueChar[j]){
                                    present=true;
                                }
                            }
                            if (!present){
                                uniqueChar[val]=keyword.charAt(i);
                                val++;
                            }
                        }
                        ch=ch.replaceAll(Character.toString(keyword.charAt(i)), "");
                    }

                    for (int i=0; i<ch.length(); i++){
                        uniqueChar[val]=ch.charAt(i);
                        val++;
                    }
                    val=0;

                    for (int i=0; i<5; i++){
                        for (int j=0; j<5; j++){
                            alphabets[i][j]=uniqueChar[val];
                            val++;
                            System.out.print(alphabets[i][j] + "\t");
                        }
                        //System.out.println();
                    }
                }

                String manageMessage(String msg){
                    int val=0;
                    int len=msg.length()-2;
                    String newTxt="";
                    String intermediate="";
                    while (len>=0){
                        intermediate=msg.substring(val, val+2);
                        if (intermediate.charAt(0)==intermediate.charAt(1)){
                            newTxt=intermediate.charAt(0) + "x" + intermediate.charAt(1);
                            msg=msg.replaceFirst(intermediate, newTxt);
                            len++;
                        }
                        len-=2;
                        val+=2;
                    }

                    if (msg.length()%2!=0){
                        msg=msg+'x';
                    }
                    return msg.toUpperCase().replaceAll("J","I").replaceAll(" ","");
                }

                void doPlayFair(String msg, String tag){
                    int val=0;
                    while (val<msg.length()){
                        searchAndEncryptOrDecrypt(msg.substring(val, val + 2), tag);
                        val+=2;
                    }
                }

                void searchAndEncryptOrDecrypt(String doubblyCh, String tag){
                    char ch1=doubblyCh.charAt(0);
                    char ch2=doubblyCh.charAt(1);
                    int row1=0, col1=0, row2=0, col2=0;
                    for (int i=0; i<5; i++){
                        for (int j=0; j<5; j++){
                            if (alphabets[i][j]==ch1){
                                row1=i;
                                col1=j;
                            }else if (alphabets[i][j]==ch2){
                                row2=i;
                                col2=j;
                            }
                        }
                    }
                    if (tag=="Encrypt")
                        encrypt(row1, col1, row2, col2);
                    else if(tag=="Decrypt")
                        decrypt(row1, col1, row2, col2);
                }

                void encrypt(int row1, int col1, int row2, int col2){
                    if (row1==row2){
                        col1=col1+1;
                        col2=col2+1;
                        if (col1>4)
                            col1=0;
                        if (col2>4)
                            col2=0;
                        encrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row1][col2]));
                    }else if(col1==col2){
                        row1=row1+1;
                        row2=row2+1;
                        if (row1>4)
                            row1=0;
                        if (row2>4)
                            row2=0;
                        encrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row2][col1]));
                    }else{
                        encrypted+=(Character.toString(alphabets[row1][col2])+Character.toString(alphabets[row2][col1]));
                    }
                }

                void decrypt(int row1, int col1, int row2, int col2){
                    if (row1==row2){
                        col1=col1-1;
                        col2=col2-1;
                        if (col1<0)
                            col1=4;
                        if (col2<0)
                            col2=4;
                        decrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row1][col2]));
                    }else if(col1==col2){
                        row1=row1-1;
                        row2=row2-1;
                        if (row1<0)
                            row1=4;
                        if (row2<0)
                            row2=4;
                        decrypted+=(Character.toString(alphabets[row1][col1])+Character.toString(alphabets[row2][col1]));
                    }else{
                        decrypted+=(Character.toString(alphabets[row1][col2])+Character.toString(alphabets[row2][col1]));
                    }
                }

                String getEncrypted(){
                    return encrypted;
                }
                String getDecrypted(){
                    return decrypted;
                }



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
