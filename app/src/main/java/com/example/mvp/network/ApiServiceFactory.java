package com.example.mvp.network;

import com.example.mvp.BuildConfig;
import com.example.mvp.utility.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {
    private static final String BASE_URL = Constants.BASE_URL;
    private static final int HTTP_READ_TIMEOUT = 120;
    private static final long HTTP_WRITE_TIMEOUT = 120;

    public static ApiService makeApiServiceService() {
        return makeApiService(makeOkHttpClient());
    }

    private static ApiService makeApiService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }

    private static OkHttpClient makeOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(makeLoggingInterceptor());
        httpClientBuilder.addInterceptor(createInterceptor());
        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    private static Interceptor createInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
    }

}