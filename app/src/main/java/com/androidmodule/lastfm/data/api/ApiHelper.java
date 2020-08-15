package com.androidmodule.lastfm.data.api;

import com.androidmodule.lastfm.data.model.Response;

import io.reactivex.Observable;

public interface ApiHelper {
    Observable<Response> getResponse(String searchKey, String apiKey, String format);
}
