package com.example.musicplayer;

import android.graphics.Bitmap;

public class Song {
    String songPath, songName;
    Bitmap bitmap;

    public Song() {
    }

    public Song(String songPath, String songName, Bitmap bitmap) {
        this.songPath = songPath;
        this.songName = songName;
        this.bitmap=bitmap;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
