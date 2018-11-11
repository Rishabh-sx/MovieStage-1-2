package com.rishabh.moviestage.moviedetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rishabh.moviestage.R;
import com.rishabh.moviestage.pojo.reviews.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Result> reviewsList;

    public ReviewsAdapter() {
        this.reviewsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reviews, viewGroup, false);
        ReviewsViewHolder viewHolder = new ReviewsViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder viewHolder, int i) {

        viewHolder.bind(viewHolder, reviewsList.get(i));

    }


    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public void setReviewList(List<Result> results) {
        reviewsList.clear();
        reviewsList.addAll(results);
        notifyDataSetChanged();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.tv_reviewer)
        TextView tvReviewer;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.tv_review)
        TextView tvReview;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ReviewsViewHolder holder, Result result) {
            holder.tvReviewer.setText(result.getAuthor());
            holder.tvReview.setText(result.getContent());
            if(getAdapterPosition()==reviewsList.size()-1)
                view.setVisibility(View.GONE);
            else
                view.setVisibility(View.VISIBLE);
        }

    }

}
