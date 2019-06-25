package android.test.api;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiKeyWord {

    private RestManager restManager;
    private Call<List<String>> getKeyWord;
    private keyWordCallback keyWordCallback;

    public ApiKeyWord(keyWordCallback keyWordCallback) {
        this.keyWordCallback = keyWordCallback;
        restManager = new RestManager();
    }

    public void getKeyWord() {

        getKeyWord = restManager.getApiService().getKeyWord();
        getKeyWord.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> data = response.body();
                    Log.d("bodyVedette",""+response.body());
                    keyWordCallback.getData(data);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if (getKeyWord.isCanceled()) {
                    Log.d("getVedette", "cancel ");
                } else
                {
                    keyWordCallback.connectFailure();
                    Log.d("getVedette", "" + t);
                }
            }
        });
    }

    public interface keyWordCallback{
        void getData(List<String> arr);
        void connectFailure();
    }
}
