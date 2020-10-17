package com.example.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class SongsFragment extends Fragment {
    RecyclerView rcSongs;
    ArrayList<Song> arrayList;
    SongAdapter adapter;
    ArrayList<SongFolder> folderList;
    String path = "";
    File f = null;
    Bitmap bitmap;
    public static final int PERMISSION_READ = 0;

    public SongsFragment() {
    }

    public static SongsFragment newInstance() {
        SongsFragment fragment = new SongsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        rcSongs = view.findViewById(R.id.rcSongs);
        arrayList = new ArrayList<>();

        if (checkPermission()) {
            new LoadFolder().execute();
        }
        return view;
    }


    private void getAllFolder(File file, String name) {
        File list[] = file.listFiles();
        boolean folderFound = false;
        File mfile;
        String directoryName = "";
        int n = 0;
        for (int i = 0; i < list.length; i++) {
            mfile = new File(file, list[i].getName());
            if (mfile.isDirectory()) {
                getAllFolder(mfile, directoryName);
            } else {
                if (list[i].getName().endsWith(".mp3")) {
                    folderFound = true;
                    n++;
                }
            }
        }
        if (folderFound) {
            folderList.add(new SongFolder(file.getName(), n));
        }

    }

    public class LoadFolder extends AsyncTask<Void, Void, ArrayList<SongFolder>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            path = Environment.getExternalStorageDirectory().toString();
            f = new File(path);
            folderList = new ArrayList<SongFolder>();
        }

        @Override
        protected ArrayList<SongFolder> doInBackground(Void... voids) {
            getAllFolder(f, f.getName());

            for (int i = 0; i < folderList.size(); i++) {
                File photoFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderList.get(i).folderName + "/");
                if (photoFolder.exists()) {
                    File[] allFiles = photoFolder.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return (name.endsWith(".mp3"));
                        }
                    });
                    for (int j = 0; j < allFiles.length; j++) {

                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(allFiles[j].getAbsolutePath());

                        byte[] data = mmr.getEmbeddedPicture();

                        if (data != null) {
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        } else {
                            bitmap = BitmapFactory.decodeFile("https://icon-library.com/images/music-file-icon/music-file-icon-17.jpg");
                        }
                        arrayList.add(new Song(allFiles[j].getAbsolutePath(), allFiles[j].getName(), bitmap));
                    }
                }
            }
            return folderList;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<SongFolder> voids) {
            super.onPostExecute(voids);
            adapter = new SongAdapter(getActivity().getApplicationContext(), arrayList);
            rcSongs.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rcSongs.setAdapter(adapter);
        }
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        new LoadFolder().execute();
                    }
                }
            }
        }
    }

}