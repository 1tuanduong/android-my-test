package android.test.api;

import android.os.Build;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private ApiService apiService;

    private String headerClient = "Android SDK: " + android.os.Build.VERSION.SDK + " (" + Build.VERSION.RELEASE + ") " + ", Device: "
            + android.os.Build.DEVICE + ", Model: " + android.os.Build.MODEL + ", Product: " + android.os.Build.PRODUCT ;


    public ApiService getApiService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                okhttp3.Request original = chain.request();

                okhttp3.Request request = original.newBuilder()
                        .header("User-Agent",headerClient)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client= httpClient.build();

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
