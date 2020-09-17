package com.example.androidtest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidtest.R;
import com.example.androidtest.adapters.ContactsAdapter;
import com.example.androidtest.model.Contacts;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

  ImageView imageView_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_contacts);

         imageView_add = (ImageView)findViewById(R.id.imageview_add_members);
         imageView_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(ContactsActivity.this,Main2Activity.class);
                 startActivity(intent);
             }
         });

    }

}
