package com.minhnpa.coderschool.flicks.api;

import com.minhnpa.coderschool.flicks.model.NowPlaying;
import com.minhnpa.coderschool.flicks.model.Popular;
import com.minhnpa.coderschool.flicks.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();

    @GET("popular")
    Call<Popular> getPopular();

    @GET("{id}/trailers")
    Call<Trailers> getYoutubes(@Path("id") long id);
}
