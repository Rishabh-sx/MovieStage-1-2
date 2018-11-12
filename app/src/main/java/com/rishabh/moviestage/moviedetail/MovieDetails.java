package com.rishabh.moviestage.moviedetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rishabh.moviestage.R;
import com.rishabh.moviestage.base.BaseActivity;
import com.rishabh.moviestage.data.DataManager;
import com.rishabh.moviestage.data.database.viewmodel.AddMoviesViewModel;
import com.rishabh.moviestage.data.database.viewmodel.AddMoviesViewModelFactory;
import com.rishabh.moviestage.data.database.viewmodel.MainViewModel;
import com.rishabh.moviestage.movielist.MovieListActivity;
import com.rishabh.moviestage.pojo.Result;
import com.rishabh.moviestage.utils.AppContants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetails extends BaseActivity implements MovieDetailView, TrailersAdapter.TrailersListener {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_vote)
    TextView tvVote;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;
    @BindView(R.id.card_trailers)
    CardView cardTrailers;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @BindView(R.id.card_reviews)
    CardView cardReviews;
    @BindView(R.id.mark_fav_unfave)
    ImageView markFavUnfave;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Result movie;
    private ReviewsAdapter reviewsAdapter;
    private TrailersAdapter trailersAdapter;
    private MovieDetailPresenter movieDetailPresenter;
    private boolean isReviewQueried;
    private boolean isTrailerQueried;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        movie = getMovie();
        ButterKnife.bind(this);
        movieDetailPresenter = new MovieDetailPresenter(this);
        movieDetailPresenter.initView();
        setupViewModel(movie.getId());

        /*
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
         supportPostponeEnterTransition();
    */
    }

    @Override
    protected int getResourceId() {
        return R.layout.layout_movie_details;
    }

    private Result getMovie() {
        return getIntent().getParcelableExtra("movie");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);

        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //      updateBackground((FloatingActionButton) findViewById(R.id.share_fab), palette);
        supportStartPostponedEnterTransition();
        setTransparentStatusBar(palette);
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    private void setTransparentStatusBar(Palette palette) {
        int color = palette.getMutedColor(getResources().getColor(R.color.colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getWindow() != null)
                getWindow().setStatusBarColor(color);
        }
    }

    public float Lightness(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        float hsl[] = new float[3];
        ColorUtils.RGBToHSL(red, green, blue, hsl);
        return hsl[2];
    }

    @Override
    public void initViews() {

        setupReviewsView();
        setupTrailersView();


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String itemTitle = movie.getOriginalTitle();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Glide.with(image.getContext())
                .load(AppContants.BASE_URL_ORIGINAL_IMAGE + movie.getPosterPath())
                .asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }


            @Override
            public boolean onResourceReady(Bitmap resource, String model,
                                           Target<Bitmap> target,
                                           boolean isFromMemoryCache,
                                           boolean isFirstResource) {

                image.setImageBitmap(resource);
                if (image.getDrawable() != null) {
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette palette) {
                            applyPalette(palette);
                        }
                    });
                }
                return true;
            }
        }).into(image);

        tvRating.setText(String.format("Release Date : %s", movie.getReleaseDate()));
        tvVote.setText(String.format("Vote Average : %s", movie.getVoteAverage()));
        title.setText(movie.getTitle());
        description.setText(movie.getOverview());

    }

    public void setupTrailersView() {
        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailersAdapter = new TrailersAdapter(this);
        rvTrailers.setAdapter(trailersAdapter);
        if (trailersAdapter.getItemCount() > 0) cardTrailers.setVisibility(View.VISIBLE);
    }

    public void setupReviewsView() {
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        reviewsAdapter = new ReviewsAdapter();
        rvReviews.setAdapter(reviewsAdapter);
        if (reviewsAdapter.getItemCount() > 0) cardReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void itemClicked(com.rishabh.moviestage.pojo.trailers.Result trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.getId()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    private void setupViewModel(final Integer id) {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getReviews(id).observe(this, new Observer<List<com.rishabh.moviestage.pojo.reviews.Result>>() {
            @Override
            public void onChanged(@Nullable List<com.rishabh.moviestage.pojo.reviews.Result> reviews) {
                if (!isReviewQueried && (reviews == null || reviews.size() == 0)) {
                    isReviewQueried = true;
                    movieDetailPresenter.getReviews(String.valueOf(id));
                }
                reviewsAdapter.setReviewList(reviews);
                if (reviewsAdapter.getItemCount() > 0) cardReviews.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getTrailers(id).observe(this, new Observer<List<com.rishabh.moviestage.pojo.trailers.Result>>() {
            @Override
            public void onChanged(@Nullable List<com.rishabh.moviestage.pojo.trailers.Result> trailers) {

                if (!isTrailerQueried && (trailers == null || trailers.size() == 0)) {
                    isTrailerQueried = true;
                    movieDetailPresenter.getTrailers(String.valueOf(id));
                }
                trailersAdapter.setTrailersList(trailers);
                if (trailersAdapter.getItemCount() > 0) cardTrailers.setVisibility(View.VISIBLE);
            }
        });

        AddMoviesViewModelFactory factory = new AddMoviesViewModelFactory(
                DataManager.getInstance().getDatabase(), movie.getId());
        // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
        // for that use the factory created above AddTaskViewModel
        final AddMoviesViewModel addMoviesViewModel
                = ViewModelProviders.of(this, factory).get(AddMoviesViewModel.class);

        // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
        addMoviesViewModel.getMovies().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result movieUpdate) {
                //viewModel.getTask().removeObserver(this);
                movie = movieUpdate;
                if (movieUpdate.isFav()) {
                    markFavUnfave.setImageResource(R.drawable.ic_fav);
                } else {
                    markFavUnfave.setImageResource(R.drawable.ic_unfav);
                }
            }
        });

    }

    @OnClick(R.id.mark_fav_unfave)
    public void onViewClicked() {

            movie.setFav(!movie.isFav());
            movieDetailPresenter.setMovieFav(movie);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
