package com.androidmodule.lastfm.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidmodule.lastfm.R;
import com.androidmodule.lastfm.data.model.Album;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context context;
    private List<Album> albumList;

    public MainAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, int position) {
        holder.textViewAlbumName.setText(albumList.get(position).getName());
        holder.textViewArtistName.setText(albumList.get(position).getArtist());
        Glide.with(context).load(albumList.get(position).getImage().get(1).getText()).into(holder.imageViewAlbumView);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewAlbumView;
        private TextView textViewAlbumName, textViewArtistName;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAlbumView = (ImageView) itemView.findViewById(R.id.album_image);
            textViewAlbumName = (TextView) itemView.findViewById(R.id.album_name);
            textViewArtistName = (TextView) itemView.findViewById(R.id.artist_name);
        }
    }
}
