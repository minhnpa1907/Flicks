package com.minhnpa.coderschool.flicks.model;

import com.google.gson.annotations.SerializedName;
import com.minhnpa.coderschool.flicks.utils.Constant;

/**
 * Created by MINH NPA on 14 Oct 2016.
 */

public class Movie {
    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

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
}
