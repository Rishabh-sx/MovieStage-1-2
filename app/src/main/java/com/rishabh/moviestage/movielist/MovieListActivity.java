package com.rishabh.moviestage.movielist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rishabh.moviestage.R;
import com.rishabh.moviestage.base.BaseActivity;
import com.rishabh.moviestage.data.database.viewmodel.MainViewModel;
import com.rishabh.moviestage.moviedetail.MovieDetails;
import com.rishabh.moviestage.pojo.MovieResponse;
import com.rishabh.moviestage.pojo.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListActivity extends BaseActivity implements MovieListView, MoviesAdapter.MovieAdapterListener {

    private static final String TAG = "MovieListActivity";

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private String filteredby;
    private MovieListPresenter presenter;
    private MoviesAdapter moviesAdapter;

    @Override
    protected int getResourceId() {
        return R.layout.activity_common_listing;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setUpAdapter();
        setupViewModel();
        filteredby = "popular";
        presenter = new MovieListPresenter(this);
        presenter.getMovies(filteredby);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> movies) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                moviesAdapter.setMoviesList(movies);
                if (movies.size() > 0) {
                    tvNoData.setVisibility(View.INVISIBLE);
                }
            else
                {
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onMoviesResponse(MovieResponse movieResponse) {
        moviesAdapter.setMoviesList(movieResponse.getResults());
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
        presenter.detachView();
    }

    @Override
    public void itemClicked(Result result) {
        startActivity(new Intent(this, MovieDetails.class).putExtra("movie", result));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_filter:
                openFilterDialog();
                return true;
            case R.id.action_fav:
                openFavMoviesScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFavMoviesScreen() {
        startActivity(new Intent(this, FavouriteMovieListActivity.class));
    }

    private void openFilterDialog() {

        final CharSequence[] options = {"Most Popular", "Highest Rated", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter By");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Most Popular")) {

                    if (!filteredby.equals("popular")) {
                        presenter.getMovies("popular");
                        filteredby = "popular";
                    }


                    dialog.dismiss();
                } else if (options[item].equals("Highest Rated")) {
                    if (!filteredby.equals("top_rated")) {
                        presenter.getMovies("top_rated");
                        filteredby = "top_rated";
                    }
                    dialog.dismiss();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }
}
