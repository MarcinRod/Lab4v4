package com.example.lab4v4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4v4.dummy.ContactsContent;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CallActivity extends AppCompatActivity {

    private MediaPlayer backgroundPlayer;
    private ContactsContent.ContactItem contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        int position = getIntent().getIntExtra("ContactPosition", 0);
        ImageView contactImage = findViewById(R.id.callImageView);
        TextView contactInfo = findViewById(R.id.callInfo);
        contact = ContactsContent.getItem(position);
        contactInfo.setText("Calling:" + contact.name + contact.surname);
        String picPath = contact.picPath;
        if(picPath != null && !picPath.isEmpty() ){
            Drawable taskDrawable;

            switch(picPath){
                case "avatar 1":taskDrawable = this.getDrawable(R.drawable.avatar_1);break;
                case "avatar 2":taskDrawable = this.getDrawable(R.drawable.avatar_2);break;
                case "avatar 3":taskDrawable = this.getDrawable(R.drawable.avatar_3);break;
                case "avatar 4":taskDrawable = this.getDrawable(R.drawable.avatar_4);break;
                case "avatar 5":
                default:
                    taskDrawable = this.getDrawable(R.drawable.avatar_5);
            }
            contactImage.setImageDrawable(taskDrawable);
        }
        else
        {
            contactImage.setImageDrawable(this.getDrawable(R.drawable.avatar_5));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(contact.ringtone != null && !contact.ringtone.isEmpty()) {
            switch(contact.ringtone) {
                case "ringtone 1":
                    backgroundPlayer = MediaPlayer.create(this, R.raw.ring01);
                    break;
                case "ringtone 2":
                    backgroundPlayer = MediaPlayer.create(this, R.raw.ring02);
                    break;
                case "ringtone 3":
                    backgroundPlayer = MediaPlayer.create(this, R.raw.ring03);
                default:

            }
        }
        else
        {
            backgroundPlayer = MediaPlayer.create(this, R.raw.ring01);
        }
        backgroundPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Set the looping parameter to true
                mp.setLooping(true); //Start the playback. //By placing the start method in the onPrepared event //we are always certain that the audio stream is prepared.
                mp.start();
                Snackbar.make(findViewById(R.id.callActivityContainer),"Playing " + contact.ringtone, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundPlayer.release();
    }

    public void dismissCall(View view) {
        Toast.makeText(this,"Call dismissed", Toast.LENGTH_LONG).show();
        finish();
    }
}