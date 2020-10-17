package com.example.musicplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    Context context;
    ArrayList<Song> arrayList;

    public SongAdapter(Context context, ArrayList<Song> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(arrayList.get(position).getBitmap());
        if (arrayList.get(position).getBitmap() == null) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_music_note_24);
        }
        holder.tvSongName.setText(arrayList.get(position).getSongName());
        holder.icAddToPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_to_playlist, null);
                final EditText edPlaylistName = view.findViewById(R.id.edPlaylistName);
                Button btnAddToPl = view.findViewById(R.id.btnAddToPl);
                btnAddToPl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edPlaylistName.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter the playlist name first", Toast.LENGTH_LONG).show();
                        } else {

                        }
                    }
                });
                builder.setView(view);
                builder.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PlaySongActivity.class);
                Bundle bundle = new Bundle();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                arrayList.get(position).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bundle.putByteArray("bitmap", byteArray);
                bundle.putString("name", arrayList.get(position).getSongName());
                bundle.putString("path", arrayList.get(position).getSongPath());
                intent.putExtra("bdSong", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongName, tvSongDur;
        ImageView imageView, icAddToPl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            imageView = itemView.findViewById(R.id.imgSongs);
            icAddToPl = itemView.findViewById(R.id.icAddToPl);
        }
    }
}
