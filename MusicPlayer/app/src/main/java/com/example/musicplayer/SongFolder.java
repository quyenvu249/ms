package com.example.musicplayer;

public class SongFolder {
    String folderName;
    int folderSize;

    public SongFolder(String folderName, int folderSize) {
        this.folderName = folderName;
        this.folderSize = folderSize;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFolderSize() {
        return folderSize;
    }

    public void setFolderSize(int folderSize) {
        this.folderSize = folderSize;
    }
}
