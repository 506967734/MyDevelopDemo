package com.demo.apicommon;


import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpPostService {
    @POST("/logout")
    Observable<String> logout();

    @POST("/update")
    Observable<String> editPreparation(@Body RequestBody Body);

    @GET("/data/sk/101010100.html")
    Observable<String> getWeather();
}
