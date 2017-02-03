package apprtc.preeyakamon.hiddinword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class play extends ActionBarActivity {

    //Explicit
    private MediaPlayer soundRadio;
    private TextView txtAnswer, txtQuestion, levelTextView, timeTextView;
    private TypedArray arrQuest;
    private int Quest_Item, intIndex, scoreAnInt = 0;
    private String[] Quest, Ans, len_ans;
    private Random rndQuest = new Random();
    private int[] timeInts = new int[]{300, 240, 180};
    private boolean aBoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        levelTextView = (TextView) findViewById(R.id.textView7);
        timeTextView = (TextView) findViewById(R.id.textView6);


        myFindIndex();


        //Show TextView
        levelTextView.setText("Level " + Integer.toString(intIndex + 1));
        timeTextView.setText(Integer.toString(timeInts[intIndex]) + " sec");

        init_view();
        Read_Question();

        //หมายถึง Index ของคำถาม
        Quest_Item = rndQuest.nextInt(250) + 1;
        Screen_Refresh();

        myLoop();

    }   // Main Method

    private void myFindIndex() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM playTABLE", null);
            cursor.moveToFirst();

            intIndex = Integer.parseInt(cursor.getString(2));
            Log.d("3febV1", "Index ที่รับมา ==> " + intIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void myLoop() {

        if (aBoolean) {
            //Do it
            timeInts[intIndex] -= 1;
            timeTextView.setText(Integer.toString(timeInts[intIndex]) + " sec");



            //Delay
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {

                        if (timeInts[intIndex] == 0) {
                            aBoolean = false;
                            myAlertPlay("หมดเวลา", "เล่นใหม่ หมดเวลาคะ");
                        } else {
                            myLoop();
                        }

                    } catch (Exception e) {
                        aBoolean = false;
                    }

                }   // run
            }, 1000);


        }   //if

    }   // myLoop

    private void myAlertPlay(String strTitle, String strMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon1);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = getIntent();
                intent.putExtra("Index", intIndex);
                finish();
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    public void onClickTex(View view) {
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

        int len1 = String.valueOf(txtAnswer.getText()).length();
        int len2 = Integer.valueOf(len_ans[Quest_Item - 1]);
        if (len1 == len2) {
            String txt1 = String.valueOf(txtAnswer.getText());
            String txt2 = Ans[Quest_Item - 1];
            if (txt1.equals(txt2)) {
                Toast.makeText(getApplicationContext(), "ถูกต้องแล้วค่ะ" + txt1, Toast.LENGTH_LONG).show();

               int[] ints = new int[]{20, 30, 40};
             //   int[] ints = new int[]{2, 3, 4};

                scoreAnInt += 1;
                if (scoreAnInt == ints[intIndex]) {

                    if (intIndex == 2) {
                        Intent intent = new Intent(play.this, Success.class);
                        startActivity(intent);
                    }   // if

                    intIndex += 1;
                    myAlertPlay("ผ่านด่านแล้ว", "ยินดีด้วย ผ่านด่านแล้ว");


                }   // if

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
        txtQuestion = (TextView) findViewById(R.id.txtQuest_p);
        txtAnswer = (TextView) findViewById(R.id.txtAns_p);

        Quest = new String[250];
        Ans = new String[250];
        len_ans = new String[250];


    }

    private void Read_Question() {

//        String[] myQuestionStrings = new String[]{"Questions_", "Questions1_", "Questions2_"};
        String[] myQuestionStrings = new String[]{"Questions_", "Questions_", "Questions_"};

        for (int i = 0; i < Quest.length; i++) {
            String quest_tag = myQuestionStrings[intIndex] + String.valueOf(i + 1);
            int resQuest = getResources().getIdentifier(quest_tag, "array", getPackageName());
            arrQuest = getResources().obtainTypedArray(resQuest);

            Quest[i] = String.valueOf(arrQuest.getString(0));
            Ans[i] = String.valueOf(arrQuest.getString(1));
            len_ans[i] = String.valueOf(arrQuest.getString(2));
        }
    }

    private void Screen_Refresh() {
        try {

            txtQuestion.setText(Quest[Quest_Item - 1]);
            txtAnswer.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {

            MainActivity.SoundMusic.pause();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }   // onPause

    @Override
    protected void onResume() {
        super.onResume();

        try {

            if (MainActivity.SoundPlaying) {
                MainActivity.SoundMusic.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // onResume
}
