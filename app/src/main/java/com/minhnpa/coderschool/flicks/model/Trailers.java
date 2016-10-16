package com.minhnpa.coderschool.flicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {
    @SerializedName("youtube")
    private List<Youtube> youtubes;

    public List<Youtube> getYoutubes() {
        return youtubes;
    }
}
