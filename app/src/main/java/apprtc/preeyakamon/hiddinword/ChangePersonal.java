package apprtc.preeyakamon.hiddinword;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePersonal extends ActionBarActivity {

    EditText etName, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal);

        etName = (EditText) findViewById(R.id.etName);
        etPass = (EditText) findViewById(R.id.etPass);

        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        etName.setText(spf.getString("idName", "null"));
        etPass.setText(spf.getString("pass", "null"));

        (findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePlayTable(etName.getText().toString(), etPass.getText().toString());
            }
        });

    }


    public void updatePlayTable(String name, String password) {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        ContentValues data = new ContentValues();
        data.put(MyManage.column_name, String.valueOf(name));
        data.put(MyManage.column_pass, String.valueOf(password));
        int updated = sqLiteDatabase.update("userTABLE", data, "_id = " + Integer.parseInt(spf.getString("id", "")), null);
        Toast.makeText(ChangePersonal.this, "เปลี่ยนแปลงข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("idName", name);
        editor.putString("pass", password);
        editor.apply();

    }

}
