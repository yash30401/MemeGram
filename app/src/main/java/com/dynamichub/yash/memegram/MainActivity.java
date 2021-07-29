package com.dynamichub.yash.memegram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView memeImageView;
    ProgressBar progressBar;

    String Currenturl=null;
    ProgressDialog mProgressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memeImageView=findViewById(R.id.memeImageView);
        progressBar=findViewById(R.id.progressBar);

        loadMeme();
    }

    private void loadMeme(){

// Instantiate the RequestQueue.
        progressBar.setVisibility(View.VISIBLE);

        String url ="https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
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

    public void next(View view){

        // Next Button Function

        loadMeme();

    }


    void DownloadImage(String fileName, String ImageURl){

        try{

            DownloadManager downloadManager=null;
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

