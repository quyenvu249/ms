package com.example.musicplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private SQLite sqLite;
    private SQLiteDatabase sqLiteDatabase;
    public static final String SQL_PLAYLIST = "create table Playlist(" +
            "playlistName text primary key, " +
            "size int)";
    public static final String TABLE_NAME = "Playlist";
    public static final String TAG = "PLaylistDAO";

    public PlaylistDAO(Context context) {
        sqLite = new SQLite(context);
        sqLiteDatabase = sqLite.getWritableDatabase();
    }

    public int addPlaylist(Playlist playlist) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("playlistName", playlist.getPlaylistName());
        contentValues.put("size", playlist.getSize());
        try {
            if (sqLiteDatabase.insert(TABLE_NAME, null, contentValues) == -1) {
                return -1;
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return 1;
    }

    public List<Playlist> getPlaylist() {
        List<Playlist> list = new ArrayList<>();
        String select = "select * from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                Playlist playlist = new Playlist(cursor.getString(cursor.getColumnIndex("playlistName")), cursor.getInt(cursor.getColumnIndex("size")));
                list.add(playlist);
            } while (cursor.moveToNext());
            cursor.close();
        }
        sqLiteDatabase.close();

        return list;
    }

    public int deleteUser(String playlistName) {
        int result = sqLiteDatabase.delete(TABLE_NAME, "playlistName=?", new String[]{playlistName});
        if (result == 0)
            return -1;
        return 1;
    }

    public int updateUser(String playlistName, int size) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("playlistName", playlistName);
        contentValues.put("size", size);
        int rs = sqLiteDatabase.update(TABLE_NAME, contentValues, "playlistName=?", new String[]{playlistName});
        if (rs == 0)
            return -1;
        return 1;
    }
}
