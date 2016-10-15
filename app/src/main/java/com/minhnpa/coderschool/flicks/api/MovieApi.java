package com.minhnpa.coderschool.flicks.api;

import com.minhnpa.coderschool.flicks.model.NowPlaying;
import com.minhnpa.coderschool.flicks.model.Popular;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();

    @GET("popular")
    Call<Popular> getPopular();
}
