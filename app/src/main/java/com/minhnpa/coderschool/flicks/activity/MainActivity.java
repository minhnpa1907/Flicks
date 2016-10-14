package com.minhnpa.coderschool.flicks.activity;

import android.os.PersistableBundle;
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

    // Handling Configuration
    static final String SAVE_ORIENTATION = "save_orientation";
    private int mSaveInt = 0;
    private Response<NowPlaying> resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMovie = (ListView) findViewById(R.id.lvMovie);
        pbLoadMovie = (ProgressBar) findViewById(R.id.pbLoadMovie);
        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);
        if (mSaveInt == 0) {
            mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
                @Override
                public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                    handleResponse(response);
                    resp = response;
                }

                @Override
                public void onFailure(Call<NowPlaying> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        } else {
            handleResponse(resp);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mSaveInt = 9001;
        savedInstanceState.putInt(SAVE_ORIENTATION, mSaveInt);
    }

    private void handleResponse(Response<NowPlaying> response) {
        lvMovie.setAdapter(new MovieAdapter(this, response.body().getMovies()));
        pbLoadMovie.setVisibility(View.GONE);
    }
}
