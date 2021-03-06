package com.minhnpa.coderschool.flicks.model;

import com.google.gson.annotations.SerializedName;
import com.minhnpa.coderschool.flicks.utils.Constant;

public class Movie {
    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("release_date")
    private String releaseDate;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return Constant.STATIC_BASE_URL + posterPath;
    }

    public String getBackdropPath() {
        return Constant.STATIC_BASE_URL + backdropPath;
    }

    public int getVoteAverage() {
        return (int) Double.parseDouble(voteAverage);
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
