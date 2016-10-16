package com.minhnpa.coderschool.flicks.activity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.minhnpa.coderschool.flicks.R;
import com.minhnpa.coderschool.flicks.api.MovieApi;
import com.minhnpa.coderschool.flicks.model.Trailers;
import com.minhnpa.coderschool.flicks.model.Youtube;
import com.minhnpa.coderschool.flicks.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends YouTubeBaseActivity {
    private TextView tvTitleDetails;
    private TextView tvReleaseDateDetails;
    private RatingBar rbRateDetails;
    private TextView tvOverViewDetails;
    private MovieApi mMovieApi;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitleDetails = (TextView) findViewById(R.id.tvTitleDetails);
        tvReleaseDateDetails = (TextView) findViewById(R.id.tvReleaseDateDetails);
        rbRateDetails = (RatingBar) findViewById(R.id.rbRateDetails);
        tvOverViewDetails = (TextView) findViewById(R.id.tvOverViewDetails);

        id = getIntent().getLongExtra("id", 0);
        tvTitleDetails.setText(getIntent().getStringExtra("title"));
        tvReleaseDateDetails.setText("Release Date: " + getIntent().getStringExtra("release_date"));
        rbRateDetails.setRating(getIntent().getIntExtra("rate", 0));
        tvOverViewDetails.setText(getIntent().getStringExtra("overview"));
        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);

        fetchTrailer();
    }

    private void fetchTrailer() {
        mMovieApi.getYoutubes(id).enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                List<Youtube> listYoutube = response.body().getYoutubes();
                LoadYoutubePlayer(listYoutube);
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {

            }
        });
    }

    private void LoadYoutubePlayer(final List<Youtube> listYoutube) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(getString(R.string.api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        if (rbRateDetails.getRating() < 5) {
                            youTubePlayer.cueVideo(listYoutube.get(0).getSource());
                        } else {
                            youTubePlayer.loadVideo(listYoutube.get(0).getSource());
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
