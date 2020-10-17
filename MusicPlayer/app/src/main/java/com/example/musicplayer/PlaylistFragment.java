package com.example.musicplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class PlaylistFragment extends Fragment {
    ListView lvPlaylist;
    ImageView icAddPl;
    PlaylistDAO playlistDAO;
    List<Playlist> playlists;
    PlaylistAdapter playlistAdapter;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        lvPlaylist = view.findViewById(R.id.lvPlaylist);
        icAddPl = view.findViewById(R.id.icAddPl);
        icAddPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialog = LayoutInflater.from(getContext()).inflate(R.layout.dialof_add_playlist, null);
                final EditText edPlaylistName = dialog.findViewById(R.id.edPlaylistName);
                //Button btnAddPl = dialog.findViewById(R.id.btnAddPl);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!edPlaylistName.getText().toString().isEmpty()) {
                            playlistDAO = new PlaylistDAO(getContext());
                            Playlist playlist = new Playlist();
                            playlist.playlistName = edPlaylistName.getText().toString();
                            if (playlistDAO.addPlaylist(playlist) > 0) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Nhập tên playlist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(dialog);
                builder.show();
            }
        });
        playlistDAO = new PlaylistDAO(getContext());
        playlists = playlistDAO.getPlaylist();
        playlistAdapter = new PlaylistAdapter(getContext(), playlists);
        lvPlaylist.setAdapter(playlistAdapter);

        return view;
    }
}