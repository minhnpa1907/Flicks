package com.minhnpa.coderschool.flicks.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhnpa.coderschool.flicks.R;
import com.minhnpa.coderschool.flicks.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        mMovies = movies;
    }

    @Override
    public int getViewTypeCount() {
        return mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getVoteAverage();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int type = 0;

        if (convertView == null) {
            type = getItemViewType(position);
            convertView = getInflatedLayoutForType(type, parent);
            viewHolder = new ViewHolder();

            if (type < 5) {
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvOverView = (TextView) convertView.findViewById(R.id.tvOverView);
            }
            viewHolder.imgCover = (ImageView) convertView.findViewById(R.id.imgCover);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FillData(viewHolder, position, type);

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvOverView;
        ImageView imgCover;
    }

    private void FillData(ViewHolder viewHolder, int position, int type) {
        Movie movie = getItem(position);

        if (type < 5) {
            // Fill the data
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvOverView.setText(movie.getOverview());
        }

        // Get configuration
        Configuration configuration = getContext().getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.placeholder_portrait)
                    .into(viewHolder.imgCover);
        } else {
            Picasso.with(getContext())
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.placeholder_landscape)
                    .into(viewHolder.imgCover);
        }
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent) {
        if (type >= 5) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_popmovie_5stars, parent, false);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }
    }
}
