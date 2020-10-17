package com.example.musicplayer;

public class Playlist {
    String playlistName;
    int size;

    public Playlist() {
    }

    public Playlist(String playlistName, int size) {
        this.playlistName = playlistName;
        this.size = size;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
