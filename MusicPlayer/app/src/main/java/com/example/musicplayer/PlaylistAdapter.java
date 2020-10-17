package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PlaylistAdapter extends BaseAdapter {
    Context context;
    List<Playlist> playlists;
    PlaylistDAO playlistDAO;

    public PlaylistAdapter(Context context, List<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
        playlistDAO = new PlaylistDAO(context);
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return playlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        TextView tvPlName = convertView.findViewById(R.id.tvPlName);
        TextView tvSize = convertView.findViewById(R.id.tvSize);
        final Playlist playlist = (Playlist) getItem(position);
        tvPlName.setText(playlist.getPlaylistName());
        tvSize.setText(playlist.getSize() + "");
        ImageView ivDelete = convertView.findViewById(R.id.imgDelete);
//        ivDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String billID = list.get(position).getBillID();
//                billDAO.deleteBill(billID);
//                Bill bill1 = list.get(position);
//                list.remove(bill1);
//                notifyDataSetChanged();
//            }
//        });
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, BillActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("billID", bill.getBillID());
//                intent.putExtra("bdBill", bundle);
//                context.startActivity(intent);
//            }
//        });
        return convertView;

    }
}
