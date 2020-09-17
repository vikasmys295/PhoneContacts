package com.example.androidtest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtest.R;
import com.example.androidtest.adapters.ContactsAdapter;
import com.example.androidtest.model.Contacts;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<Contacts> selectUsers ;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ContactsAdapter adapter;
    Cursor phones;
    RelativeLayout relativeLayout;
    ImageView imageView,imageView1;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        relativeLayout = (RelativeLayout)findViewById(R.id.layout_groups_header);
        imageView = (ImageView)findViewById(R.id.imageview_arrow);
        imageView1 = (ImageView)findViewById(R.id.imageview_arrow1);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        selectUsers = new ArrayList<Contacts>();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                imageView1.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        });
        showContacts();

    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            LoadContact loadContact = new LoadContact();
            loadContact.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {

                Intent intent = new Intent(Main2Activity.this,ContactsActivity.class);
                startActivity(intent);

                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {

                }

                while (phones.moveToNext()) {
                   // Bitmap bit_thumb = null;
                   // String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    Contacts selectUser = new Contacts();
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUsers.add(selectUser);

                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // sortContacts();
            ArrayList<Contacts> removed=new ArrayList<>();
            ArrayList<Contacts> contacts=new ArrayList<>();
            for(int i=0;i<selectUsers.size();i++){
                Contacts inviteFriendsProjo = selectUsers.get(i);

                if(inviteFriendsProjo.getName().matches("\\d+(?:\\.\\d+)?")||inviteFriendsProjo.getName().trim().length()==0){
                    removed.add(inviteFriendsProjo);
                    Log.d("Removed Contact",new Gson().toJson(inviteFriendsProjo));
                }else{
                    contacts.add(inviteFriendsProjo);
                }
            }
            contacts.addAll(removed);
            selectUsers=contacts;
            adapter = new ContactsAdapter(inflater, selectUsers);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);

        }
    }
}
