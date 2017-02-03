package apprtc.preeyakamon.hiddinword;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class play extends ActionBarActivity {

    MediaPlayer soundRadio;



    TextView txtAnswer,txtQuestion;
    TypedArray arrQuest;
    int Quest_Item;
    String[] Quest,Ans,len_ans;

    Random rndQuest = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        init_view();
        Read_Question();

        //หมายถึง Index ของคำถาม
        Quest_Item = rndQuest.nextInt(250) + 1;
        Screen_Refresh();
    }   // Main Method

    public void onClickTex(View view) {
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
        }

        int len1 = String.valueOf(txtAnswer.getText()).length();
        int len2 = Integer.valueOf(len_ans[Quest_Item-1]);
        if (len1 == len2) {
            String txt1 = String.valueOf(txtAnswer.getText());
            String txt2 = Ans[Quest_Item-1];
            if (txt1.equals(txt2)) {
                Toast.makeText(getApplicationContext(),"ถูกต้องแล้วค่ะ"+txt1,Toast.LENGTH_LONG).show();

                soundRadio = MediaPlayer.create(getBaseContext(),R.raw.jetsons1);
                soundRadio.start();
                //เสียงเวลาถูก

                Quest_Item = rndQuest.nextInt(250)+1;
                Screen_Refresh(); }
            else {
                Toast.makeText(getApplicationContext(),"ลองคิดใหม่นะค่ะ",Toast.LENGTH_LONG).show();

                soundRadio = MediaPlayer.create(getBaseContext(),R.raw.a_boing);
                soundRadio.start();
                //เสียงเวลาผิด
            }
        }
        soundRadio = MediaPlayer.create(getBaseContext(),R.raw.corkpop2);
        soundRadio.start();
        //เสียงปุ่มตัวอักษร
    }
    public void onClickClear(View view) {
        txtAnswer.setText("");

        soundRadio = MediaPlayer.create(getBaseContext(),R.raw.bloop22);
        soundRadio.start();
        //เสียงปุ่มcl

    }

    private void init_view() {
        txtQuestion = (TextView)findViewById(R.id.txtQuest);
        txtAnswer = (TextView)findViewById(R.id.txtAns);

        Quest = new String[250];
        Ans = new String[250];
        len_ans = new String[250];

    }
    private void Read_Question(){
        for (int i=0; i<250; i++){
            String quest_tag="Questions_"+String.valueOf(i+1);
            int resQuest = getResources().getIdentifier(quest_tag,"array",getPackageName());
            arrQuest = getResources().obtainTypedArray(resQuest);

            Quest[i] = String.valueOf(arrQuest.getString(0));
            Ans[i] = String.valueOf(arrQuest.getString(1));
            len_ans[i] = String.valueOf(arrQuest.getString(2));
        }
    }
    private void Screen_Refresh(){
        try {

            txtQuestion.setText(Quest[Quest_Item-1]);
            txtAnswer.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.SoundMusic.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.SoundPlaying) {
            MainActivity.SoundMusic.start();
        }
    }
}
