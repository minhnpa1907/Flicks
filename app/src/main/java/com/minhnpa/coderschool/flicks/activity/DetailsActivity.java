package com.minhnpa.coderschool.flicks.activity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.minhnpa.coderschool.flicks.R;

public class DetailsActivity extends YouTubeBaseActivity {
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private RatingBar rbRate;
    private TextView tvOverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        rbRate = (RatingBar) findViewById(R.id.rbRate);
        tvOverView = (TextView) findViewById(R.id.tvOverView);

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvReleaseDate.setText(getIntent().getStringExtra("release_date"));
        rbRate.setRating(getIntent().getIntExtra("rate", 0));
        tvOverView.setText(getIntent().getStringExtra("overview"));

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(getString(R.string.api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo("5xVh-7ywKpE");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
