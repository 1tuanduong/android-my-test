package android.test.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.test.R;
import android.test.database.DB;
import android.test.database.DBHelper;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private DB database;
    private boolean checkFirst=true;
    public static String FISRT_OPEN = "";
    private SharedPreferences sharedPreferencesHomePage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        configViews();
        waitToMain();
    }

    private void configViews(){
        database = new DB(getApplicationContext());
        SharedPreferences sharedPreferencesFirstOpen = getSharedPreferences( FISRT_OPEN, MODE_PRIVATE );
        checkFirst = sharedPreferencesFirstOpen.getBoolean("first_open", true);

        if (checkFirst) {

            // add Keywords to database
            database.addKeyWords(getApplicationContext());

            // update sharepreferen
            SharedPreferences.Editor editor = sharedPreferencesFirstOpen.edit();
            editor.putBoolean("first_open", false);
            editor.commit();
        }
    }

    private void  waitToMain(){

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goToHomePage();
                    }
                }, SPLASH_TIME_OUT);

            }
        });
    }

    private void goToHomePage(){

        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
