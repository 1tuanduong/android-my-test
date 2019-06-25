package android.test.view.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.R;
import android.test.api.ApiKeyWord;
import android.test.database.DB;
import android.test.model.KeyWord;
import android.test.utils.NetworkUtils;
import android.test.view.adapter.Adapter;
import android.util.Log;
import android.view.View;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ApiKeyWord.keyWordCallback {

    private RecyclerView recyclerView;
    private DB database;
    private ArrayList<KeyWord> keyWordsList;
    private Adapter adapter;
    private ApiKeyWord apiKeyWord;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        configView();

        fetchData();

    }

    private void configView(){
        apiKeyWord = new ApiKeyWord(this);
        keyWordsList = new ArrayList<>();
        database = new DB(getApplicationContext());
        //database.getProfilesCount();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getKeyWordFromDB(){

        Cursor cursor = database.getKeyWord();

        if (cursor.getCount() > 0){
            if (cursor.moveToFirst()) {
                do {
                    KeyWord keyWord = new KeyWord(cursor.getInt(cursor.getColumnIndex("position")),cursor.getString(cursor.getColumnIndex("object_name")));
                    keyWordsList.add(keyWord);

                } while (cursor.moveToNext());
            }

        }
        adapter.add(keyWordsList);


    }

    private void fetchData(){
        if (getNetworkAvailability()) {//internet
            apiKeyWord.getKeyWord();
        }
        else { // No internet
            getKeyWordFromDB();
        }
    }

    private boolean getNetworkAvailability(){
        boolean checkNetwork = true;

        if (getApplication() != null && getApplication().getApplicationContext() != null) {
            checkNetwork = NetworkUtils.isNetworkAvailable(getApplication().getApplicationContext());

                if (checkNetwork) {
                    Log.d("network","Yes");
                } else {
                    Log.d("network","No");
                }

        }
        return checkNetwork;
    }


    @Override
    public void getData(List<String> arr) {

        for (int i = 0; i < arr.size(); i++){
            KeyWord keyWord = new KeyWord(i,arr.get(i));
            keyWordsList.add(keyWord);

        }
        adapter.add(keyWordsList);
    }

    @Override
    public void connectFailure() {
        Log.d("connectFailure","Fail");
        getKeyWordFromDB();
    }
}
