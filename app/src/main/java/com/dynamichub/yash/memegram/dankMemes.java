package com.dynamichub.yash.memegram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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


import org.json.JSONException;

import java.io.File;

public class dankMemes extends AppCompatActivity {


    ImageView memeImageView;
    ProgressBar progressBar;
    ConstraintLayout danklayout;

    String Currenturl=null;

    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dank_memes);

        memeImageView=findViewById(R.id.memeImageView);
        progressBar=findViewById(R.id.progressBar);
        danklayout=findViewById(R.id.dankLayout);

        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        boolean first_star=prefs.getBoolean("firstStart",true);

        dialog=new Dialog(dankMemes.this);
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

        danklayout.setOnTouchListener(new OnSwipeTouchListener(dankMemes.this){
            @Override
            public void onSwipeLeft() {
                dankloadMeme();
            }
        });


        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Okay Dialoge Button Click Function
                dialog.dismiss();
            }
        });
        dankloadMeme();



    }

    private void dankloadMeme(){

// Instantiate the RequestQueue.
        progressBar.setVisibility(View.VISIBLE);

        String url ="https://meme-api.herokuapp.com/gimme/dankmemes";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {

                    try {
                        Currenturl=response.getString("url");
                        Glide.with(dankMemes.this).load(Currenturl).listener(new RequestListener<Drawable>() {
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