package apprtc.preeyakamon.hiddinword;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by masterUNG on 1/6/2017 AD.
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    //Explicit
    private Context context;
    public static final String database_name = "AddWord.db";
    private static final int database_version = 1;

    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "Name text, " +
            "User text, " +
            "Password text);";

    private static final String create_play_table = "create table playTABLE (" +
            "_id integer primary key, " +
            "idUSER text, " +
            "Level text, " +
            "Score text, " +
            "DateTime text);";

    private static final String create_statistic_table = "create table statisticTABLE (" +
            "_id text, " +
            "score text, " +
            "dateTime text);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
        sqLiteDatabase.execSQL(create_play_table);
        sqLiteDatabase.execSQL(create_statistic_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertStatistic(String id, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            //final String dateTime = Calendar.getInstance().getTime().toString();
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");//dd/MM/yyyy
            Date now = new Date();
            final String dateTime = sdfDate.format(now);
            ContentValues cv = new ContentValues();
            cv.put("_id", id);
            cv.put("score", String.valueOf(score));
            cv.put("dateTime", dateTime);
            Long l = db.insert("statisticTABLE", null, cv);
            Log.d("StatisticLog", "Inserted: " + cv.toString());
            Log.d("StatisticLog", "Inserted: " + l);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public List<JSONObject> getStatisticList(Context ctx) {
        SharedPreferences spf = ctx.getSharedPreferences("user", MODE_PRIVATE);
        SQLiteDatabase db = this.getReadableDatabase();
        List<JSONObject> resp = new ArrayList<>();
        Cursor c = null;
        String _id = spf.getString("idUser", "");
        String sql = "SELECT * FROM statisticTABLE WHERE _id = '" + _id + "' Order by score desc LIMIT 5";
        Log.d("StatisticLog", sql);
        try {
            c = db.rawQuery(sql, null);
            Log.d("StatisticLog", "Cursor Size: " + c.getCount());
            while (c.moveToNext()) {
                JSONObject obj = new JSONObject();
                obj.put("_id", c.getString(c.getColumnIndex("_id")));
                obj.put("score", c.getString(c.getColumnIndex("score")));
                obj.put("dateTime", c.getString(c.getColumnIndex("dateTime")));
                resp.add(obj);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return resp;
    }
}   // Main Class




