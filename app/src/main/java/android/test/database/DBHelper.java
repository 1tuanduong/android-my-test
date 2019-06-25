package android.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
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

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private Context context;

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {

        if (instance == null)
            instance = new DBHelper(context);

        return instance;
    }



    public static  final class Database{

        public static final String DB_NAME = "Database";
        public static final int DB_VERSION = 2;

        public static final String LIMIT = " LIMIT ";

        // Table: keywords
        public static final String TABLE_KEYWORDS  = "KeyWord";
        public static final String KEYWORDS_POSITION = "position";
        public static final String OBJECT_NAME = "object_name";
        public static final String ODER_BY_CATEGORY_PARENT_DESC = " ORDER BY " + KEYWORDS_POSITION + " ASC ";


        public static final String CREATE_TABLE_KEYWORDS = "CREATE TABLE "
                + TABLE_KEYWORDS +
                "(" +
                KEYWORDS_POSITION + " INT NOT NULL," +
                OBJECT_NAME + " TEXT NOT NULL)";



        public static final String DROP_TABLE_KEYWORDS = "DROP TABLE IF EXIST "
                + TABLE_KEYWORDS;

        public static final String SELECT_TABLE_KEYWORDS = "SELECT * FROM "
                + TABLE_KEYWORDS + ODER_BY_CATEGORY_PARENT_DESC;

    }

    public DBHelper(Context context) {
        super(context, Database.DB_NAME, null, Database.DB_VERSION);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            Log.d("DBHelper", "Tạo data");
            //tao lai

            db.execSQL(Database.CREATE_TABLE_KEYWORDS);

            Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();

        } catch (SQLException ex) {
            Log.d("DBHelper", "Looi Tạo data");
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d("DBHelper", "Cập nhật data");
        db.execSQL(Database.DROP_TABLE_KEYWORDS);

        this.onCreate(db);
    }

    public boolean deleteCache() {

        boolean rsDelete = false;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            // delete table
            String checkQuery = " DELETE  FROM  " + Database.TABLE_KEYWORDS ;
            db.execSQL(checkQuery);
            //db.execSQL(Database.DROP_TABLE_KEYWORDS);
            db.execSQL(Database.CREATE_TABLE_KEYWORDS);

            rsDelete = true;


        } catch (Exception e) {

        }
        return rsDelete;
    }

    public boolean addKeyWords(Context ct){
        boolean checkAdd = false;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            String checkQuery = "SELECT * FROM " + Database.TABLE_KEYWORDS;
            cursor = db.rawQuery(checkQuery, null);
            if (cursor.getCount() > 0) {

                String deleteQuery = " DELETE  FROM  " + Database.TABLE_KEYWORDS;
                db.execSQL(deleteQuery);
            }
            if (cursor.getCount() <= 0) {
            String jsonText = Helper.readText(ct, R.raw.keywords);
            JSONArray jsonArray = new JSONArray(jsonText);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonRoot = jsonArray.getJSONObject(i);

                    ContentValues values = new ContentValues();

                    values.put(Database.KEYWORDS_POSITION, i);
                    values.put(Database.OBJECT_NAME, jsonRoot.getString("name"));
                    db.insert(Database.TABLE_KEYWORDS, null, values);


            }
            cursor.close();
            checkAdd = true;
             }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("AddCategoryParent", "DBException");

        }
        return checkAdd;
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, Database.TABLE_KEYWORDS);
        db.close();
        return count;
    }
}
