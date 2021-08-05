package com.dynamichub.yash.memegram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

public class Categories extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

    }

    //toolbar menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (id==R.id.about){



            dialog=new Dialog(Categories.this);
            dialog.setContentView(R.layout.dialogabout);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
            }

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
            dialog.show();

        }
        return true;
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