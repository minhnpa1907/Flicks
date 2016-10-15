package com.minhnpa.coderschool.flicks.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.minhnpa.coderschool.flicks.R;
import com.minhnpa.coderschool.flicks.adapter.MovieAdapter;
import com.minhnpa.coderschool.flicks.api.MovieApi;
import com.minhnpa.coderschool.flicks.model.NowPlaying;
import com.minhnpa.coderschool.flicks.model.Popular;
import com.minhnpa.coderschool.flicks.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout;
    private ListView lvMovie;
    private ProgressBar pbLoadMovie;
    private MovieApi mMovieApi;
    private MovieAdapter movieAdapter;

    // Handling Configuration
    static final String SAVE_ORIENTATION = "save_orientation";
    private int mSaveInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularMovie();
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        lvMovie = (ListView) findViewById(R.id.lvMovie);
        pbLoadMovie = (ProgressBar) findViewById(R.id.pbLoadMovie);
        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);
        if (mSaveInt == 0) {
            fetchMovie();
        } else {
            lvMovie.setAdapter(movieAdapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(SAVE_ORIENTATION, 9001);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSaveInt = savedInstanceState.getInt(SAVE_ORIENTATION);
    }

    private void fetchMovie() {
        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                handleNowPlayingResponse(response);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
        Toast.makeText(this, "Now Playing Movie", Toast.LENGTH_SHORT).show();
    }

    private void handleNowPlayingResponse(Response<NowPlaying> response) {
        movieAdapter = new MovieAdapter(this, response.body().getMovies());
        lvMovie.setAdapter(movieAdapter);
        pbLoadMovie.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    private void fetchPopularMovie() {
        mMovieApi.getPopular().enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(Call<Popular> call, Response<Popular> response) {
                handlePopularResponse(response);
            }

            @Override
            public void onFailure(Call<Popular> call, Throwable t) {

            }
        });
        Toast.makeText(this, "Popular Movie", Toast.LENGTH_SHORT).show();
    }

    private void handlePopularResponse(Response<Popular> response) {
        movieAdapter = new MovieAdapter(this, response.body().getMovies());
        lvMovie.setAdapter(movieAdapter);
        pbLoadMovie.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }
}
