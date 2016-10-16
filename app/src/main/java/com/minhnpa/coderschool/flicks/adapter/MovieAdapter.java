package com.minhnpa.coderschool.flicks.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhnpa.coderschool.flicks.R;
import com.minhnpa.coderschool.flicks.activity.PlayActivity;
import com.minhnpa.coderschool.flicks.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, -1, movies);
        mMovies = movies;
    }

    @Override
    public int getViewTypeCount() {
        return mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position).getVoteAverage();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int rate = 0;

        if (convertView == null) {
            rate = getItemViewType(position);
            convertView = getInflatedLayoutForType(rate);
            viewHolder = new ViewHolder();

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.imgCover = (ImageView) convertView.findViewById(R.id.imgCover);
            viewHolder.btnPlay = (ImageButton) convertView.findViewById(R.id.btnPlay);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FillData(viewHolder, position, rate);

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView imgCover;
        ImageButton btnPlay;
    }

    private void FillData(ViewHolder viewHolder, int position, int rate) {
        final Movie movie = getItem(position);

        if (null != movie) {
            // Get configuration
            Configuration configuration = getContext().getResources().getConfiguration();
            switch (configuration.orientation) {
                case Configuration.ORIENTATION_PORTRAIT:
                    // Fill the data
                    if (rate >= 5) {
                        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), PlayActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putLong("id", movie.getId());

                                intent.putExtras(bundle);
                                getContext().startActivity(intent);
                            }
                        });
                        Picasso.with(getContext())
                                .load(movie.getBackdropPath())
                                .transform(new RoundedCornersTransformation(10, 10))
                                .placeholder(R.drawable.placeholder_landscape)
                                .into(viewHolder.imgCover);
                    } else {
                        viewHolder.tvTitle.setText(movie.getTitle());
                        viewHolder.tvOverview.setText(movie.getOverview());
                        Picasso.with(getContext())
                                .load(movie.getPosterPath())
                                .transform(new RoundedCornersTransformation(10, 10))
                                .placeholder(R.drawable.placeholder_portrait)
                                .into(viewHolder.imgCover);
                    }
                    break;
                case Configuration.ORIENTATION_LANDSCAPE:
                    // Fill the data
                    viewHolder.tvTitle.setText(movie.getTitle());
                    viewHolder.tvOverview.setText(movie.getOverview());
                    if (rate >= 5) {
                        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), PlayActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putLong("id", movie.getId());

                                intent.putExtras(bundle);
                                getContext().startActivity(intent);
                            }
                        });
                        Picasso.with(getContext())
                                .load(movie.getBackdropPath())
                                .transform(new RoundedCornersTransformation(10, 10))
                                .placeholder(R.drawable.placeholder_landscape)
                                .into(viewHolder.imgCover);
                    } else {
                        Picasso.with(getContext())
                                .load(movie.getPosterPath())
                                .transform(new RoundedCornersTransformation(10, 10))
                                .placeholder(R.drawable.placeholder_landscape)
                                .into(viewHolder.imgCover);
                    }
                    break;
            }
        }
    }

    private View getInflatedLayoutForType(int rate) {
        if (rate >= 5) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_popmovie_5stars, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        }
    }
}
