package apprtc.preeyakamon.hiddinword;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Authen extends ActionBarActivity implements View.OnClickListener {

    //Explicit
    private Button signInButton, signUpButton;
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private MyAlert myAlert;
    private String tag = "6janV1";
    private String[] loginStrings = new String[4];
    private int indexAnInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);

        //Setup
        myManage = new MyManage(Authen.this);
        myAlert = new MyAlert(Authen.this);

        bindWidget();


    }   // Main Method

    //คือการ เก็บ event จากการคลิก ปุ่ม Master
    private void buttonController() {

        signInButton.setOnClickListener(Authen.this);
        signUpButton.setOnClickListener(Authen.this);

    }

    //คือการผูก ตัวแปร กับ Widget ที่ XML
    private void bindWidget() {

        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        buttonController();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button:
                //Click SignIn
                checkSignIn();
                break;
            case R.id.button2:
                //Click SignUp
                startActivity(new Intent(Authen.this, SignUp.class));
                break;

        }   // switch

    }   // onClick

    private void checkSignIn() {

        //Get Value
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equalsIgnoreCase("") || passwordString.equalsIgnoreCase("")) {
            //Have Space
            myAlert.myDialog(R.drawable.icon2,
                    getResources().getString(R.string.title_haveSpace),
                    getResources().getString(R.string.message_hameSpace));
        } else if (checkDatabase()) {
            //No Data on My Database
            myAlert.myDialog(R.drawable.icon3,
                    getResources().getString(R.string.title_noData),
                    getResources().getString(R.string.message_noData));
        } else if (checkUser()) {
            //User False
            myAlert.myDialog(R.drawable.icon4,
                    getResources().getString(R.string.title_userFalse),
                    getResources().getString(R.string.message_userFalse));
        } else if (!passwordString.equalsIgnoreCase(loginStrings[3])) {
            myAlert.myDialog(R.drawable.icon1,
                    getResources().getString(R.string.title_passFalse),
                    getResources().getString(R.string.message_passFalse));
        } else {
            Toast.makeText(Authen.this, "Welcome " + loginStrings[1], Toast.LENGTH_SHORT).show();
            Log.d("3febV2", "result ==> " + userCheckLevel());

            SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();
            editor.putString("id", loginStrings[0]);
            editor.putString("idUser", userString);
            editor.putString("idName", loginStrings[1]);
            editor.putString("pass", loginStrings[3]);
            editor.apply();

            if (userCheckLevel()) {
                //No Data
                Intent intent = new Intent(Authen.this, Menu.class);
                intent.putExtra("test", true);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Authen.this, Menu.class);
                intent.putExtra("test", false);
                startActivity(intent);
                finish();
            }


        }


    }   // checkSignIn

    private boolean userCheckLevel() {

        boolean result = false;

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            String rawQuery = String.format("SELECT * FROM playTABLE WHERE idUSER = \"%s\"",
                    userString);
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



    private boolean checkUser() {

        boolean result;
        final String rawQuery = String.format("SELECT * FROM userTABLE WHERE %s = \"%s\" and %s = \"%s\"",
                MyManage.column_user,
                userString,
                MyManage.column_pass,
                passwordString);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
        result = cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            if (userString.equals(cursor.getString(cursor.getColumnIndex(MyManage.column_user)))) {
                result = false;
                loginStrings[0] = cursor.getString(cursor.getColumnIndex(MyManage.column_id));
                loginStrings[1] = cursor.getString(cursor.getColumnIndex(MyManage.column_name));
                loginStrings[2] = cursor.getString(cursor.getColumnIndex(MyManage.column_user));
                loginStrings[3] = cursor.getString(cursor.getColumnIndex(MyManage.column_pass));
            }

            cursor.moveToNext();
        }   //for
        if (cursor != null) cursor.close();

        return result;
    }

    //ทำหน้าที่ ตรวจว่า userTABLE มีข้อมูล หรือ เป็น ตารางเปล่า (ยังไม่มีใครสมัคร)
    private boolean checkDatabase() {

        boolean result = false;

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
        cursor.moveToFirst();
        Log.d(tag, "cursor.getCount ==> " + cursor.getCount());
        if (cursor.getCount() == 0) {
            //No Data in Database
            result = true;
        }
        cursor.close();
        Log.d(tag, "checkData return ==> " + result);
        return result;
    }


}   // Main Class
