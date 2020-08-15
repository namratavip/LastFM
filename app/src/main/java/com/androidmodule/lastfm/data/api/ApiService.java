package com.androidmodule.lastfm.data.api;

import com.androidmodule.lastfm.data.model.Response;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("?method=album.search")
    Observable<Response> getResponse(@Query("album")String searchKey, @Query("api_key")String apiKey,@Query("format") String json);
}
