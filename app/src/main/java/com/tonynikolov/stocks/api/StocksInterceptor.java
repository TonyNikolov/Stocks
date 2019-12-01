package com.tonynikolov.stocks.api;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class StocksInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();
    HttpUrl originalHttpUrl = original.url();

    HttpUrl url = originalHttpUrl.newBuilder()
        .addQueryParameter("apikey", "8HNSAA27THBUM7N2")
        .build();

    Request.Builder requestBuilder = original.newBuilder()
        .url(url);

    Request request = requestBuilder.build();
    return chain.proceed(request);
  }
}
