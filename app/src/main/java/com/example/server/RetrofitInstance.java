package com.example.server;

import android.util.Log;

import com.example.monet_transfer.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofitInstance;
    private RetrofitInstance() {

    }
    public static Retrofit getInstance() {
        if(retrofitInstance == null) {
            try {

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);

                Interceptor basicAuth = new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type","application/x-www-form-urlencoded")
                                .build();
                        return chain.proceed(request);
                    }
                };

                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                        .addInterceptor(basicAuth)
                        .addInterceptor(logging)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build();

                Gson gs = new GsonBuilder().setLenient().create();

                retrofitInstance = new retrofit2.Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gs))
                        .build();
            } catch (Exception e) {
                Log.d("Exception", "Exception : "+e.getMessage());
            }
        }
        return retrofitInstance;
    }
    private final static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
}
