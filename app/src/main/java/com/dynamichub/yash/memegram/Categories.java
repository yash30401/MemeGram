package com.dynamichub.yash.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void MainMemes(View view){

        //Memes button function start here

        Intent mainmemesintent=new Intent(Categories.this,MainActivity.class);
        startActivity(mainmemesintent);
    }

    public void dankmemes(View view){

        // Dank memes button fucntion Starts From Here
        Intent dankmemesintent=new Intent(Categories.this,dankMemes.class);
        startActivity(dankmemesintent);

    }

    public void wholesomemes(View view){
        Intent wholesomememesintent=new Intent(Categories.this,wholesomemes.class);
        startActivity(wholesomememesintent);
    }

    public void animememes(View view){
        // Animememes button function starts from here

        Intent animememesintent=new Intent(Categories.this,animememes.class);
        startActivity(animememesintent);
    }

    public void pubgmemes(View view){
        // Animememes button function starts from here

        Intent pubgememesintent=new Intent(Categories.this,pubgMemes.class);
        startActivity(pubgememesintent);
    }

}