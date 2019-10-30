package com.manuelrojas.geomusic.data.repository.network;

import com.manuelrojas.geomusic.BuildConfig;
import com.manuelrojas.geomusic.data.utils.TLSSocketFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RetrofitClient {

    private static Retrofit retrofit = null;
    private OkHttpClient httpClient = null;
//    private static final String BASE_URL = "https://api.github.com";
    private static final String BASE_URL = "http://ws.audioscrobbler.com";

    private static Map<String, String> headers = new HashMap<>();

    @Inject
    public RetrofitClient() {}

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public Retrofit getRetrofit() {
        httpClient = createOkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();

        return retrofit;
    }

    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(createTLSSocketFactory(), createTLSSocketFactory().getTrustManager());
        if ( !headers.isEmpty() ) {
            for (Map.Entry<String, String> entry : headers.entrySet()){
                builder.addInterceptor(new HeaderInterceptor(entry.getKey(), entry.getValue()));
            }
        }
        return builder.addInterceptor(loggingInterceptor).build();
    }

    //Change SSL(Default) to TLS for devices running android API < 20
    private  TLSSocketFactory createTLSSocketFactory() {
        TLSSocketFactory tlsSocketFactory = null;
        try {
            tlsSocketFactory = new TLSSocketFactory();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return tlsSocketFactory;
    }

}