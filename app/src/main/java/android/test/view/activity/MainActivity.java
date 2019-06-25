package android.test.view.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.R;
import android.test.database.DB;
import android.test.model.KeyWord;
import android.test.view.adapter.Adapter;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DB database;
    private ArrayList<KeyWord> keyWordsList;
    private Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        configView();

        getKeyWord();
    }

    private void configView(){

        keyWordsList = new ArrayList<>();
        database = new DB(getApplicationContext());
        database.getProfilesCount();
        Log.d("count",database.getProfilesCount()+"");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getKeyWord(){

        Cursor cursor = database.getKeyWord();

        if (cursor.getCount() > 0){
            if (cursor.moveToFirst()) {
                do {
                    KeyWord keyWord = new KeyWord(cursor.getInt(cursor.getColumnIndex("position")),cursor.getString(cursor.getColumnIndex("object_name")));
                    keyWordsList.add(keyWord);
                    Log.d("testLine46",": "+ keyWord.getName() + "    : "+keyWordsList.size());

                } while (cursor.moveToNext());
            }

        }
        adapter.add(keyWordsList);


    }


}
