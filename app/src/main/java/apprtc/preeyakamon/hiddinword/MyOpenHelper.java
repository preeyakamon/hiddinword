package apprtc.preeyakamon.hiddinword;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 1/6/2017 AD.
 */

public class MyOpenHelper extends SQLiteOpenHelper{

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

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
        sqLiteDatabase.execSQL(create_play_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}   // Main Class
