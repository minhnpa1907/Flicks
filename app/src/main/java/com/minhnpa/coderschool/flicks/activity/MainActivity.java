package com.minhnpa.coderschool.flicks.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.minhnpa.coderschool.flicks.R;
import com.minhnpa.coderschool.flicks.adapter.MovieAdapter;
import com.minhnpa.coderschool.flicks.api.MovieApi;
import com.minhnpa.coderschool.flicks.model.NowPlaying;
import com.minhnpa.coderschool.flicks.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {
    private ListView lvMovie;
    private ProgressBar pbLoadMovie;
    private MovieApi mMovieApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMovie = (ListView) findViewById(R.id.lvMovie);
        pbLoadMovie = (ProgressBar) findViewById(R.id.pbLoadMovie);
        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);
        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void handleResponse(Response<NowPlaying> response) {
        lvMovie.setAdapter(new MovieAdapter(this, response.body().getMovies()));
        pbLoadMovie.setVisibility(View.GONE);
    }
}
