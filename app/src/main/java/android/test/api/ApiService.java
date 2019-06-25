package android.test.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    String BASE_URL = "https://raw.githubusercontent.com";

    @GET("/tikivn/android-home-test/v2/keywords.json")
    Call<List<String>> getKeyWord();
}
