package com.flickrphotosearchapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    @GET("services/feeds/photos_public.gne?tagmode=any&per_page=40&format=json")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> getFlickrFeed(@Query("tags") String searchTerm);
}
