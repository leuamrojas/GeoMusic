package com.manuelrojas.geomusic.data.repository.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private String header;
    private String value;
    Request.Builder builder;
    Request request;

    public HeaderInterceptor(String header, String value) {
        this.header = header;
        this.value = value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//
//        Request.Builder builder = request.newBuilder()
//                .header("Authorization",
//                Credentials.basic("aUsername", "aPassword"));
//
//        Request newRequest = builder.build();
//        return chain.proceed(newRequest);

        Request request = chain.request();

        Request.Builder builder = request.newBuilder()
                .header(header, value);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);

    }
}
