package com.example.yandextranslater;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface YandexAPI {
    public static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    //TODO: put your api key here
    public static final String API_KEY = "<put your api key here>";


    @POST("translate")
    Call<Result> translate(@Query("text") String text, @Query("lang") String lang,
                           @Query("key") String key);

    @POST("getLangs")
    Call<Langs> getLangs(@Query("ui") String ui, @Query("key") String key);

}
