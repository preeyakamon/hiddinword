package apprtc.preeyakamon.hiddinword;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;


public class TestLevel extends ActionBarActivity {

    //Explicit
    private MediaPlayer soundRadio;
    private TextView txtAnswer, txtQuestion, timeTextView, tvScore;
    private TypedArray arrQuest;
    private int Quest_Item, timeAnInt = 60, scoreAnInt = 0;
    private String[] Quest, Ans, len_ans;
    private boolean aBoolean = true;
    private Button btnSkip;

    Random rndQuest = new Random();
    EditText etAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_level);

        timeTextView = (TextView) findViewById(R.id.textView5);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        init_view();
        Read_Question();

        //หมายถึง Index ของคำถาม
        Quest_Item = rndQuest.nextInt(250) + 1;
        Screen_Refresh();

        myLoop();

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvScore.setText("คะแนน " + scoreAnInt);

        (findViewById(R.id.btnDle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDel();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quest_Item = rndQuest.nextInt(250) + 1;
                Screen_Refresh();
            }
        });

        etAns.addTextChangedListener(new TextWatcher() {
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

    }   // Main Method

    private void myLoop() {

        if (aBoolean) {

            //Do it
            timeTextView.setText(Integer.toString(timeAnInt) + " Sec");


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (timeAnInt == 0) {
                        aBoolean = false;

                        int i = 0; // 0 test level
                        if (scoreAnInt >= 6) {
                            i = 3;
                        } else if (scoreAnInt >= 3) {
                            i = 2;
                        } else {
                            i = 1;
                        }

//                        MyAlert myAlert = new MyAlert(TestLevel.this);
//                        myAlert.myDialogmaster(R.drawable.icon1, "Your Score",
//                                "Score = " + Integer.toString(scoreAnInt) + "\n" +
//                                        "Your Level " + Integer.toString(i + 1),
//                                play.class,
//                                i);

                        myDialogmaster(i);

                    } else {
                        timeAnInt -= 1;
                        myLoop();
                    }
                }
            }, 1000);

        }   // if

    }   // myLoop

    private void myDialogmaster(final int level) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon1);
        builder.setTitle("Score");
        builder.setMessage("Score = " + Integer.toString(scoreAnInt) + "\n" + "Your Level = " + Integer.toString(level));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                addLevelToSQLite(level);
                onUpdateLevel(level);

                Intent intent = new Intent(TestLevel.this, playable.class);
                intent.putExtra("Index", level);
                intent.putExtra("total", scoreAnInt);
                startActivity(intent);
                finish();

                dialogInterface.dismiss();
            }
        });
        if (!this.isFinishing()) {
            builder.show();
        }


    }   // myDialot

    private void addLevelToSQLite(int level) {
        // level : 1 , 2 , 3
        Calendar calendar = Calendar.getInstance();

        MyManage myManage = new MyManage(this);
        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        String idUser = spf.getString("idUser", "");
        myManage.addValueToPlay(idUser, Integer.toString(level), Integer.toString(scoreAnInt), calendar.getTime().toString());

    }

    public void onClickText(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btnA:
                    txtAnswer.append("A");
                    break;
                case R.id.btnB:
                    txtAnswer.append("B");
                    break;
                case R.id.btnC:
                    txtAnswer.append("C");
                    break;
                case R.id.btnD:
                    txtAnswer.append("D");
                    break;
                case R.id.btnE:
                    txtAnswer.append("E");
                    break;
                case R.id.btnF:
                    txtAnswer.append("F");
                    break;
                case R.id.btnG:
                    txtAnswer.append("G");
                    break;
                case R.id.btnH:
                    txtAnswer.append("H");
                    break;
                case R.id.btnI:
                    txtAnswer.append("I");
                    break;
                case R.id.btnJ:
                    txtAnswer.append("J");
                    break;
                case R.id.btnK:
                    txtAnswer.append("K");
                    break;
                case R.id.btnL:
                    txtAnswer.append("L");
                    break;
                case R.id.btnM:
                    txtAnswer.append("M");
                    break;
                case R.id.btnN:
                    txtAnswer.append("N");
                    break;
                case R.id.btnO:
                    txtAnswer.append("O");
                    break;
                case R.id.btnP:
                    txtAnswer.append("P");
                    break;
                case R.id.btnQ:
                    txtAnswer.append("Q");
                    break;
                case R.id.btnR:
                    txtAnswer.append("R");
                    break;
                case R.id.btnS:
                    txtAnswer.append("S");
                    break;
                case R.id.btnT:
                    txtAnswer.append("T");
                    break;
                case R.id.btnU:
                    txtAnswer.append("U");
                    break;
                case R.id.btnV:
                    txtAnswer.append("V");
                    break;
                case R.id.btnW:
                    txtAnswer.append("W");
                    break;
                case R.id.btnX:
                    txtAnswer.append("X");
                    break;
                case R.id.btnY:
                    txtAnswer.append("Y");
                    break;
                case R.id.btnZ:
                    txtAnswer.append("Z");
                    break;
            }   // switch
       }

        int len1 = String.valueOf(txtAnswer.getText()).length();
        //int len1 = String.valueOf(etAns.getText()).length();
        int len2 = Integer.valueOf(len_ans[Quest_Item - 1]);
        if (len1 == len2) {
            String txt1 = String.valueOf(txtAnswer.getText());
           // String txt1 = String.valueOf(etAns.getText());
            String txt2 = Ans[Quest_Item - 1];
            if (txt1.equalsIgnoreCase(txt2)) {
                Toast.makeText(getApplicationContext(), "ถูกต้องแล้วค่ะ" + txt1, Toast.LENGTH_LONG).show();

                scoreAnInt += 1;
                tvScore.setText("คะแนน " + scoreAnInt);
                Log.d("3febV1", "Score ==> " + scoreAnInt);

                soundRadio = MediaPlayer.create(getBaseContext(), R.raw.jetsons1);
                soundRadio.start();
                //เสียงเวลาถูก

                Quest_Item = rndQuest.nextInt(250) + 1;
                Screen_Refresh();
            } else {
                Toast.makeText(getApplicationContext(), "ลองคิดใหม่นะค่ะ", Toast.LENGTH_LONG).show();

                soundRadio = MediaPlayer.create(getBaseContext(), R.raw.a_boing);
                soundRadio.start();
                //เสียงเวลาผิด
            }
        }
        soundRadio = MediaPlayer.create(getBaseContext(), R.raw.corkpop2);
        soundRadio.start();
        //เสียงปุ่มตัวอักษร
    }

    public void onClickClear(View view) {
        txtAnswer.setText("");

        soundRadio = MediaPlayer.create(getBaseContext(), R.raw.bloop22);
        soundRadio.start();
        //เสียงปุ่มcl

    }

    private void init_view() {
        txtQuestion = (TextView) findViewById(R.id.txtQuest);
        txtAnswer = (TextView) findViewById(R.id.txtAns);
        etAns = (EditText) findViewById(R.id.etAns);

        Quest = new String[250];
        Ans = new String[250];
        len_ans = new String[250];

    }

    private void Read_Question() {
        for (int i = 0; i < 250; i++) {
            String quest_tag = "Questions_" + String.valueOf(i + 1);
            int resQuest = getResources().getIdentifier(quest_tag, "array", getPackageName());
            arrQuest = getResources().obtainTypedArray(resQuest);

            Quest[i] = String.valueOf(arrQuest.getString(0));
            Ans[i] = String.valueOf(arrQuest.getString(1));
            len_ans[i] = String.valueOf(arrQuest.getString(2));
        }
    }

    private void Screen_Refresh() {
        txtQuestion.setText(Quest[Quest_Item - 1]);
        txtAnswer.setText("");
        etAns.setText("");
    }


    @Override
    protected void onPause() {
        super.onPause();
        //MainActivity.SoundMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (MainActivity.SoundPlaying) {
//            MainActivity.SoundMusic.start();
//        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("คุณต้องการกลับเมนู ใช่หรือไม่?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent in = new Intent(TestLevel.this, Menu.class);
                startActivity(in);
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", null);
        alertDialog.show();
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

    public void onUpdateLevel(int level){
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        SharedPreferences spf = getSharedPreferences("user", MODE_PRIVATE);
        ContentValues data = new ContentValues();
        data.put("Level", String.valueOf(level));
        sqLiteDatabase.update("playTABLE", data, "idUser = '" + spf.getString("idUser", "") + "'", null);
    }
}
