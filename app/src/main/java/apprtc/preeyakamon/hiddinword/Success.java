package apprtc.preeyakamon.hiddinword;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Success extends ActionBarActivity {

    Button btnBack, btnExit;
    boolean saved = false;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        score = getIntent().getExtras().getInt("score");
        ((TextView)findViewById(R.id.tvScore)).setText("รวม " + score + " คะแนน");

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStatistic();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Success.this);
                        alertDialog.setTitle("คุณต้องการบันทึกระดับของคุณ เพื่อกลับไปเล่นหน้าใหม่หรือไม่ ?");
                        alertDialog.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Keep
                                ongoto();
                            }
                        });
                        alertDialog.setNegativeButton("เริ่มต้นใหม่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Reset
                                onResetLevel();
                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        });

        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStatistic();
                Intent intent = new Intent(Success.this, Statistic.class);
                startActivity(intent);
            }
        });
    }

    public void onResetLevel(){
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        ContentValues data = new ContentValues();
        data.put("Level", String.valueOf(1));
        sqLiteDatabase.update("playTABLE", data, "idUser = '" + spf.getString("idUser", "") + "'", null);
        ongoto();
    }

    public void ongoto(){
        Intent in = new Intent(Success.this, TestLevel.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed() {
       saveStatistic();
        Intent intent = new Intent(Success.this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void saveStatistic() {
        if (saved) {
            return;
        }
        MyOpenHelper db = new MyOpenHelper(Success.this);
        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        String _id = spf.getString("idUser", "");

        db.insertStatistic(_id, score);
        Log.d("StatisticLog", "ID: " + _id + ", Score: " + score);
        saved = true;
    }
}
