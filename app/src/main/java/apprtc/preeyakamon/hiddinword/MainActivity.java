package apprtc.preeyakamon.hiddinword;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {

    public static boolean SoundPlaying;
    public static MediaPlayer SoundMusic;

    MediaPlayer soundRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundMusic = MediaPlayer.create(this, R.raw.apple_music);
        SoundMusic.setLooping(true);
        SoundMusic.start();
        SoundPlaying = true;

        setContentView(R.layout.activity_main);
    }

    public void onClickSound(View view) {

        ImageButton btnSound =(ImageButton)findViewById(R.id.btnSound);

        if (!SoundPlaying) {
            SoundMusic.start();
            SoundPlaying = true;
            btnSound.setImageResource(R.drawable.open);
        }
        else {
            SoundMusic.pause();
            SoundPlaying = false;
            btnSound.setImageResource(R.drawable.clo);
        }
    }
    public void clickGoactivity_play(View view) {
        Intent nextIntent = new Intent(this,play.class);
        startActivity(nextIntent);

    }
    @Override
    protected  void onPause(){
        super.onPause();
        SoundMusic.pause();
        if (this.isFinishing()) {
            SoundMusic.stop();
            SoundMusic.release();
            SoundMusic = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (SoundPlaying) {
            SoundMusic.start();
        }
    }
}
