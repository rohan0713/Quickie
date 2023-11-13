package com.delivery.quickie.network;

import com.delivery.quickie.data.cResponse;
import com.delivery.quickie.data.response;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Retrofit {

    private static final String base_url = "https://www.themealdb.com/api/json/v1/1/";

    public static Services services = null;

    public static Services getServices(){

        if(services == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            services = retrofit.create(Services.class);
        }

        return services;
    }

    public interface Services{
        @GET("filter.php")
        Call<response> meals(@Query("a") String a);

        @GET("list.php?a=list")
        Call<cResponse> cuisine();

    }
}
