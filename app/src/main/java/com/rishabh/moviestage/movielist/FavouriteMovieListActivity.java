package com.rishabh.moviestage.movielist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rishabh.moviestage.R;
import com.rishabh.moviestage.base.BaseActivity;
import com.rishabh.moviestage.data.database.viewmodel.MainViewModel;
import com.rishabh.moviestage.moviedetail.MovieDetails;
import com.rishabh.moviestage.pojo.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class FavouriteMovieListActivity extends BaseActivity implements MoviesAdapter.MovieAdapterListener {

    private static final String TAG = "MovieListActivity";

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private MoviesAdapter moviesAdapter;

    @Override
    protected int getResourceId() {
        return R.layout.activity_common_listing;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setTitle("Favourite Movies");
        setUpAdapter();
        setupViewModel();

    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> movies) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                if(movies.size()>0) {
                    tvNoData.setVisibility(View.INVISIBLE);
                }else {

                    tvNoData.setVisibility(View.VISIBLE);
                }
                moviesAdapter.setMoviesList(movies);

            }
        });
    }


    public void setUpAdapter() {
        int columnCount = getResources().getInteger(R.integer.list_column_count);
        GridLayoutManager glm =
                new GridLayoutManager(this, columnCount);
        rv.setLayoutManager(glm);
        moviesAdapter = new MoviesAdapter(this);
        rv.setAdapter(moviesAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void itemClicked(Result result) {
        startActivity(new Intent(this, MovieDetails.class).putExtra("movie", result));
    }

}
