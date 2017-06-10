package apprtc.preeyakamon.hiddinword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 1/6/2017 AD.
 */

public class MyManage {

    //Explicit
    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_name = "Name";
    public static final String column_user = "User";
    public static final String column_pass = "Password";

    public static final String play_table = "playTABLE";
    public static final String column_idUser = "idUSER";
    public static final String column_level = "Level";
    public static final String column_score = "Score";
    public static final String column_date = "DateTime";


    public MyManage(Context context) {
        this.context = context;

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   // Constructor

    //ทำหน้าที่ เพิ่ม Data ไปที่ SQLite
    public long addValueToUserTable(String strName,
                                    String strUser,
                                    String strPassword) {

        // เปลี่ยน ตัวอักษร ไปเป็น จำนวนเพื่อเก็บ ใน SQLite
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name, strName);
        contentValues.put(column_user, strUser);
        contentValues.put(column_pass, strPassword);
        return sqLiteDatabase.insert(user_table, null, contentValues);
    }

    public int checkDuplicateUser(String user) {
        final String rawQuery = String.format("SELECT * FROM userTABLE WHERE %s = \"%s\"",
                MyManage.column_user,
                user);
        Cursor c = sqLiteDatabase.rawQuery(rawQuery, null);
        return c.getCount();
    }

    public long addValueToPlay(String stridUSER,
                               String strLevel,
                               String strScore,
                               String strDateTime) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_idUser, stridUSER);
        contentValues.put(column_level, strLevel);
        contentValues.put(column_score, strScore);
        contentValues.put(column_date, strDateTime);

        return sqLiteDatabase.insert(play_table, null, contentValues);
    }


}   // Main Class
