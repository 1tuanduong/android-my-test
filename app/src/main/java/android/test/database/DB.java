package android.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.R;
import android.test.utils.Helper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DB extends SQLiteOpenHelper {

     public static final String DATABASE_NAME ="DB";
     public static final String TABLE_KEYWORDS  = "KeyWord";
     public static final String KEYWORDS_POSITION = "position";
     public static final String OBJECT_NAME = "object_name";
    public static final String ODER_BY_CATEGORY_PARENT_DESC = " ORDER BY " + KEYWORDS_POSITION + " ASC ";

    public static final String SELECT_TABLE_KEYWORDS = "SELECT * FROM "
            + TABLE_KEYWORDS + ODER_BY_CATEGORY_PARENT_DESC;
     private Context context;

     public DB(Context context) {
        super(context, DATABASE_NAME,null, 1);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQuery = "CREATE TABLE "+TABLE_KEYWORDS +" (" +
                KEYWORDS_POSITION +" integer primary key, "+
                OBJECT_NAME + " TEXT)";
        db.execSQL(sqlQuery);
        //Toast.makeText(context, "Database saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_KEYWORDS);
        onCreate(db);
        //Toast.makeText(context, "Database dropped", Toast.LENGTH_SHORT).show();

    }

    public boolean addKeyWords(Context ct){
        boolean checkAdd = false;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            String checkQuery = "SELECT * FROM " + TABLE_KEYWORDS;
            cursor = db.rawQuery(checkQuery, null);
            if (cursor.getCount() > 0) {

                String deleteQuery = " DELETE  FROM  " + TABLE_KEYWORDS;
                db.execSQL(deleteQuery);
            }
            if (cursor.getCount() <= 0) {
                String jsonText = Helper.readText(ct, R.raw.keywords);
                JSONArray jsonArray = new JSONArray(jsonText);

                String[] jsonString = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++){
                    jsonString[i] = jsonArray.getString(i);
                    ContentValues values = new ContentValues();
                    values.put(KEYWORDS_POSITION, i);
                    values.put(OBJECT_NAME, jsonString[i]);
                    db.insert(TABLE_KEYWORDS, null, values);
                }

                cursor.close();
                checkAdd = true;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();

        }
        return checkAdd;
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_KEYWORDS);
        db.close();
        return count;
    }

    public Cursor getKeyWord() {

        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(SELECT_TABLE_KEYWORDS, null);

        return cursor;

    }

}
