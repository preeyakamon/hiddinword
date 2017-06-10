package apprtc.preeyakamon.hiddinword;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu extends ActionBarActivity {

    ImageView imgstc, imgplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imgstc = (ImageView) findViewById(R.id.imgstc);
        imgstc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Statistic.class);
                startActivity(intent);
            }
        });

        imgplay = (ImageView) findViewById(R.id.imgplay);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCheckLevel()) {
                    Intent intent = new Intent(Menu.this, TestLevel.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Menu.this, playable.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        (findViewById(R.id.imgchange)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, ChangePersonal.class));
            }
        });
    }

    private boolean userCheckLevel() {

        boolean result = false;

        try {
            SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            String rawQuery = String.format("SELECT * FROM playTABLE WHERE idUSER = \"%s\"",
                    spf.getString("idUser", ""));
            Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
            cursor.moveToFirst();

            if (cursor.getCount() == 0) {
                return true;    // ฺไม่มีข้อมูล
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }   // userCheck

    public void onBackPressed(){
        Intent intent = new Intent(Menu.this, Authen.class);
        startActivity(intent);
    }
}
