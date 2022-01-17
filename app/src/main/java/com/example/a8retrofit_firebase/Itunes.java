package com.example.a8retrofit_firebase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Itunes {

    class Result {
        List<Invizimal> documents;
    }

    class Invizimal {
        String name;
        Fields fields;
        String createTime;
        String updateTime;
    }

    class Fields{
        StringValue nombre, elemento, imagen, fase, generacion;
    }

    class StringValue{
        String stringValue;
    }

    public static Api api = new Retrofit.Builder()
            //.baseUrl("https://itunes.apple.com/")
            //.baseUrl(https://firestore.googleapis.com/v1/projects/invizimals-d928d/databases/(default)/documents/Invizimals)
            .baseUrl("https://firestore.googleapis.com/v1/projects/invizimals-d928d/databases/(default)/documents/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {
        @GET("Invizimals/")
        Call<Result> buscar(/*@Query("term") String texto*/);
    }
}
