package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class PlaySongActivity extends AppCompatActivity {
    ImageView imgSong;
    TextView tvName, tvTimePlaying, tvTotalTime;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    ImageView btnPlay;

    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgSong = findViewById(R.id.imgSong);
        tvName = findViewById(R.id.tvName);
        tvTimePlaying = findViewById(R.id.tvTimePlaying);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        seekBar = findViewById(R.id.seekbar);
        btnPlay = findViewById(R.id.btnPlay);


        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("bdSong");

        byte[] byteArray = bundle.getByteArray("bitmap");
        if (byteArray == null) {
            imgSong.setImageResource(R.drawable.ic_baseline_music_note_24);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imgSong.setImageBitmap(bitmap);
        }

        tvName.setText(bundle.getString("name"));

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(PlaySongActivity.this, Uri.parse(bundle.getString("path")));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    btnPlay.setEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        btnPlay.setEnabled(true);
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                });
            }
        });


    }
}