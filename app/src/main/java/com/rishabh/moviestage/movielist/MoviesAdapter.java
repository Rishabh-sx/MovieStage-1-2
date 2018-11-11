package com.rishabh.moviestage.movielist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rishabh.moviestage.R;
import com.rishabh.moviestage.pojo.Result;
import com.rishabh.moviestage.utils.AppContants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Result> movieList;
    private MovieAdapterListener listener;
    private int replyFetchedForPosition;


    public MoviesAdapter(MovieAdapterListener listener) {
        movieList = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMoviesList(List<Result> results) {
        movieList.clear();
        movieList.addAll(results);
        notifyDataSetChanged();
    }

    public interface MovieAdapterListener {
        void itemClicked(Result result);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        /*@BindView(R.id.title)
        TextView title;*/
/*
        @BindView(R.id.release_date)
        TextView releaseDate;
        @BindView(R.id.popularity)
        TextView popularity;
        @BindView(R.id.card)
        CardView card;
*/

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MovieViewHolder holder, Result result) {
            Glide.with(holder.thumbnail.getContext()).load(AppContants.BASE_URL_w500_IMAGE + result.getPosterPath()).asBitmap().into(holder.thumbnail);

          //  holder.title.setText(result.getOriginalTitle());
          /*  holder.releaseDate.setText(result.getReleaseDate().substring(0,4));
            holder.popularity.setText( String.valueOf(result.getPopularity()));
          */  holder.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.card:
                    listener.itemClicked(movieList.get(getAdapterPosition()));
                    break;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int i) {

        viewHolder.bind(viewHolder, movieList.get(i));

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movie, viewGroup, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }
}
