package com.ddr.logic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://ddr-airport-back-kfzd5spnca-vp.a.run.app")
                    //.baseUrl("http://localhost:8080") // for testing
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /*
    It would be nice to have documentation.

    Used Singleton pattern to access a Retrofit object to do http requests everywhere in the app, maintaining only 1 instance of the Retrofit class.

    Singleton pattern: https://refactoring.guru/design-patterns/singleton
     */
}