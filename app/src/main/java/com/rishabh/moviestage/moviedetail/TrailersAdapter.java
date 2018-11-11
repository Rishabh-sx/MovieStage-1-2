package com.rishabh.moviestage.moviedetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rishabh.moviestage.R;
import com.rishabh.moviestage.pojo.Result;
import com.rishabh.moviestage.pojo.trailers.Trailers;
import com.rishabh.moviestage.utils.AppContants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private List<com.rishabh.moviestage.pojo.trailers.Result> trailersList;
    private TrailersListener listener;
    private int replyFetchedForPosition;


    public TrailersAdapter(TrailersListener listener) {
        trailersList = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    public void setTrailersList(List<com.rishabh.moviestage.pojo.trailers.Result> results) {
        trailersList.clear();
        trailersList.addAll(results);
        notifyDataSetChanged();
    }

    public interface TrailersListener {
        void itemClicked(com.rishabh.moviestage.pojo.trailers.Result trailer);
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;
    
        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(TrailersViewHolder holder, com.rishabh.moviestage.pojo.trailers.Result result) {
            Glide.with(holder.thumbnail.getContext()).load(AppContants.PREFIX_URL_YOUTUBE_IMAGE
                    + result.getKey() +AppContants.SUFIX_URL_YOUTUBE_IMAGE).asBitmap().into(holder.thumbnail);
            holder.thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.thumbnail:
                    listener.itemClicked(trailersList.get(getAdapterPosition()));
                    break;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder viewHolder, int i) {

        viewHolder.bind(viewHolder, trailersList.get(i));

    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_trailer_movie, viewGroup, false);
        TrailersViewHolder viewHolder = new TrailersViewHolder(view);
        return viewHolder;
    }
}
