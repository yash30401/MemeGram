package com.dynamichub.yash.memegram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;

import android.app.Dialog;
import android.app.DownloadManager;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;

import java.io.File;



public class MainActivity extends AppCompatActivity {

    ImageView memeImageView;
    ProgressBar progressBar;

    String Currenturl=null;
    ConstraintLayout constraintLayout;

    private FirebaseAnalytics firebaseAnalytics;

    Dialog dialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memeImageView=findViewById(R.id.memeImageView);
        progressBar=findViewById(R.id.progressBar);



        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        boolean first_star=prefs.getBoolean("firstStart",true);

        dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.customdialoge_box_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;

        Button okay=dialog.findViewById(R.id.okayBtn);

        SharedPreferences prefs2=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();

        if(first_star){
            dialog.show();
        }

        firebaseAnalytics=FirebaseAnalytics.getInstance(this);
        constraintLayout=findViewById(R.id.constLayout);

        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this){
            @Override
            public void onSwipeLeft() {
                loadMeme();

            }
        });



        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Okay Dialoge Button Click Function
                dialog.dismiss();
            }
        });

        loadMeme();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getDrawable(R.drawable.ic_baseline_more_vert_24));
        }



    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if (id==R.id.about){



            dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialogabout);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
            }

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
            dialog.show();

        }else if(id==R.id.privacyPolicy){

            dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialogprivacy);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
            }

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
            dialog.show();
        }else if(id==R.id.disclaimer){

            dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialogdiaclaimer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
            }

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
            dialog.show();
        }else if(id==R.id.terms){

            dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialogterms);
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

    private void loadMeme(){

// Instantiate the RequestQueue.
        progressBar.setVisibility(View.VISIBLE);

        String url ="https://meme-api.herokuapp.com/gimme/memes";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {

                    try {
                         Currenturl=response.getString("url");
                        Glide.with(MainActivity.this).load(Currenturl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(memeImageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {

                });

// Add the request to the RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void share(View view){

        // Share Button Function

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,Currenturl);
        Intent chooser = Intent.createChooser(intent,"Share this meme using....");
        startActivity(chooser);


    }



    void DownloadImage(String fileName, String ImageURl){

        try{

            DownloadManager downloadManager;
            downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloaduri=Uri.parse(ImageURl);

            DownloadManager.Request request=new DownloadManager.Request(downloaduri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle(fileName).setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+fileName+".jpg");

            downloadManager.enqueue(request);
            Toast.makeText(this,"Image Downloaded",Toast.LENGTH_LONG).show();

        }catch (Exception e){

            Toast.makeText(this,"Image Downloading Failed", Toast.LENGTH_SHORT).show();

        }
    }

    public void downloadMeme(View view){

        // This function is for downloading meme in gallery

        DownloadImage("memeImage",Currenturl);

    }







}

