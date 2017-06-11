package apprtc.preeyakamon.hiddinword;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by apimun on 5/30/17.
 */

public class playable extends ActionBarActivity {

    // Variable.
    private MediaPlayer soundRadio;
    private TextView txtAnswer, txtQuestion, levelTextView, timeTextView;
    private TypedArray arrQuest;
    private int Quest_Item, intIndex = 0, scoreAnInt = 0, currentLevel = 0, scoreTotal = 0;
    private String[] Quest, Ans, len_ans;
    private Random rndQuest = new Random();
    private int[] timeInts;
    private boolean aBoolean = true;
    private TextView tvScore;
    EditText etAns_p;

    int[] ints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Binding Widget.
        levelTextView = (TextView) findViewById(R.id.textView7);
        timeTextView = (TextView) findViewById(R.id.textView6);
        tvScore = (TextView) findViewById(R.id.tvScore);

        // initial.
        ints = new int[]{10, 20, 30}; // จำนวนข้อที่ต้องตอบให้ถูกในแต่ละ level โดยเรียงจาก level 1 2 3 ตามลำดับ
        timeInts = new int[]{300, 240, 180};

        // get Level i.e. 1, 2 or 3
        currentLevel = getCurrentLevel();
        intIndex = currentLevel > 0 ? currentLevel - 1 : 0;
        Log.d("poon", "Index: " + intIndex);
        /* set index for find question array. ใช้ if แบบสั้นหมายความว่า
        ถ้า currentLevel > 0 ให้ intIndex = currentLevel - 1
        แต่ถ้าเท่ากับ 0 หรือน้อยกว่า ให้ intIndex = 0 */

        // setup Display level.
        levelTextView.setText("Level " + Integer.toString(currentLevel));
        timeTextView.setText(String.valueOf(timeInts[intIndex]));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            scoreTotal = bundle.containsKey("total") ? bundle.getInt("total", 0) : 0;
        }

        init_view();
        Read_Question();

        //หมายถึง Index ของคำถาม
        Quest_Item = rndQuest.nextInt(250) + 1;
        Screen_Refresh();
        countDownTime();

        tvScore.setText(scoreAnInt + "/" + ints[intIndex]);
        (findViewById(R.id.btnSkip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quest_Item = rndQuest.nextInt(250) + 1;
                Screen_Refresh();
            }
        });

        // กดปุ่ม Del.
        (findViewById(R.id.btnDle_p)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDel();
            }
        });

        (findViewById(R.id.btnCLE_p)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtAnswer != null) txtAnswer.setText("");
            }
        });

        etAns_p.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onClickText(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init_view() {
        txtQuestion = (TextView) findViewById(R.id.txtQuest_p);
        txtAnswer = (TextView) findViewById(R.id.txtAns_p);
        etAns_p = (EditText) findViewById(R.id.etAns_p) ;

        Quest = new String[250];
        Ans = new String[250];
        len_ans = new String[250];

    }

    private void Read_Question() {
        String[] myQuestionStrings = new String[]{"Questions_", "Questions1_", "Questions2_"};
        for (int i = 0; i < Quest.length; i++) {
            String quest_tag = myQuestionStrings[intIndex] + String.valueOf(i + 1);
            int resQuest = getResources().getIdentifier(quest_tag, "array", getPackageName());
            arrQuest = getResources().obtainTypedArray(resQuest);
            Quest[i] = String.valueOf(arrQuest.getString(0));
            Ans[i] = String.valueOf(arrQuest.getString(1));
            len_ans[i] = String.valueOf(arrQuest.getString(2));
        }
    }

    private int getCurrentLevel() {
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
            String rawQuery = String.format("SELECT * FROM playTABLE WHERE idUser = \"%s\"",
                    spf.getString("idUser", ""));
            Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
            cursor.moveToFirst();
            return Integer.parseInt(cursor.getString(2));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void Screen_Refresh() {
        try {
            txtQuestion.setText(Quest[Quest_Item - 1]);
            txtAnswer.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickText(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btnA_p:
                    txtAnswer.append("A");
                    break;
                case R.id.btnB_p:
                    txtAnswer.append("B");
                    break;
                case R.id.btnC_p:
                    txtAnswer.append("C");
                    break;
                case R.id.btnD_p:
                    txtAnswer.append("D");
                    break;
                case R.id.btnE_p:
                    txtAnswer.append("E");
                    break;
                case R.id.btnF_p:
                    txtAnswer.append("F");
                    break;
                case R.id.btnG_p:
                    txtAnswer.append("G");
                    break;
                case R.id.btnH_p:
                    txtAnswer.append("H");
                    break;
                case R.id.btnI_p:
                    txtAnswer.append("I");
                    break;
                case R.id.btnJ_p:
                    txtAnswer.append("J");
                    break;
                case R.id.btnK_p:
                    txtAnswer.append("K");
                    break;
                case R.id.btnL_p:
                    txtAnswer.append("L");
                    break;
                case R.id.btnM_p:
                    txtAnswer.append("M");
                    break;
                case R.id.btnN_p:
                    txtAnswer.append("N");
                    break;
                case R.id.btnO_p:
                    txtAnswer.append("O");
                    break;
                case R.id.btnP_p:
                    txtAnswer.append("P");
                    break;
                case R.id.btnQ_p:
                    txtAnswer.append("Q");
                    break;
                case R.id.btnR_p:
                    txtAnswer.append("R");
                    break;
                case R.id.btnS_p:
                    txtAnswer.append("S");
                    break;
                case R.id.btnT_p:
                    txtAnswer.append("T");
                    break;
                case R.id.btnU_p:
                    txtAnswer.append("U");
                    break;
                case R.id.btnV_p:
                    txtAnswer.append("V");
                    break;
                case R.id.btnW_p:
                    txtAnswer.append("W");
                    break;
                case R.id.btnX_p:
                    txtAnswer.append("X");
                    break;
                case R.id.btnY_p:
                    txtAnswer.append("Y");
                    break;
                case R.id.btnZ_p:
                    txtAnswer.append("Z");
                    break;

            }
        }

        int lenAns = String.valueOf(txtAnswer.getText()).length();
        //int lenAns = String.valueOf(etAns_p.getText()).length();
        int lenCorrect = Integer.valueOf(len_ans[Quest_Item - 1]);
        if (lenAns == lenCorrect) {
            final String ans = txtAnswer.getText().toString();
            //final String ans = etAns_p.getText().toString();
            final String correct = Ans[Quest_Item - 1];
            if (ans.equalsIgnoreCase(correct)) { // ตอบถูก
                Toast.makeText(getApplicationContext(), "ถูกต้องแล้วค่ะ " + ans, Toast.LENGTH_SHORT).show();
                scoreAnInt += 1; // + คะแนน เพื่อเอาไปตรวจสอบว่าตอบครบหรือยัง
                scoreTotal += 1;
                tvScore.setText(scoreAnInt + "/" + ints[intIndex]);
                Log.d("PointOfAnswer", "point: " + scoreTotal);
                if (scoreAnInt == ints[intIndex]) {
                    if (intIndex == 2) {
                        Intent intent = new Intent(playable.this, Success.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                        intent.putExtra("score", scoreTotal);
                        startActivity(intent);
                        finish();
                    }   // if
                    intIndex += 1;
                    currentLevel += 1;
                    updatePlayTable(currentLevel);
                    scoreAnInt = 0;
                    myAlertPlay("ผ่านด่านแล้ว", "ยินดีด้วย ผ่านด่านแล้ว");
                }

                soundRadio = MediaPlayer.create(getBaseContext(), R.raw.jetsons1);
                soundRadio.start();
                //เสียงเวลาถูก

                Quest_Item = rndQuest.nextInt(250) + 1;
                Screen_Refresh();
            } else {
                Toast.makeText(getApplicationContext(), "ลองคิดใหม่นะค่ะ", Toast.LENGTH_SHORT).show();
                soundRadio = MediaPlayer.create(getBaseContext(), R.raw.a_boing);
                soundRadio.start();
                //เสียงเวลาผิด
            }
        } else {

        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Exit");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.setNegativeButton("No", null);
        alertDialog.show();
    }

    private void countDownTime() {
        if (aBoolean) {
            //Do it
            timeInts[intIndex] -= 1;
            timeTextView.setText(Integer.toString(timeInts[intIndex]) + " sec");
            //Delay 1 sec.
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (timeInts[intIndex] == 0) {
                            aBoolean = false;
                            myAlertPlay("หมดเวลา", "เล่นใหม่ หมดเวลาแล้ว");
                        } else {
                            countDownTime();
                        }
                    } catch (Exception e) {
                        aBoolean = false;
                    }

                }   // run
            }, 1000);
        }   //if
    }   // countDownTime

    private void myAlertPlay(final String strTitle, String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon3);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                intent.putExtra("Index", "หมดเวลาแล้ว ต้องทำเร็วกว่านี้หน่อยนะ".equalsIgnoreCase(strTitle) ? intIndex : intIndex + 1);
                intent.putExtra("total", scoreTotal);
                startActivity(intent);
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.show();
    }

    public void onClickDel() {
        if (txtAnswer != null) {
            String ans = txtAnswer.getText().toString(); // ดึงคำตอบออกมาเก็บใน ans ก่อน
            if (ans.length() > 0) { // ตรวจสอบว่า เราได้ใส่ตัวอักษรไปหรือยัง
                ans = ans.substring(0, ans.length() - 1); // ให้ตัดตัวอักษรตัวสุดท้ายออกแล้วเก็บเข้าไปในตัวแปร ans
                txtAnswer.setText(ans); // ในค่าลงไปใน TextView เหมือนเดิม
            }
        }
    }

    public void updatePlayTable(int level) {
        if (level > 3) level = 3;
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        ContentValues data = new ContentValues();
        data.put("Level", String.valueOf(level));
        int updated = sqLiteDatabase.update("playTABLE", data, "idUser = '" + spf.getString("idUser", "") + "'", null);
        Log.d("PlayLog", "Level : " + level + ", Updated: " + updated);
        Log.d("PlayLog", "idUser: " + spf.getString("idUser", ""));
    }


}
