package apprtc.preeyakamon.hiddinword;

import android.content.Intent;
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
                boolean test = getIntent().getExtras().getBoolean("test", false);
                if (test) {
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

}
