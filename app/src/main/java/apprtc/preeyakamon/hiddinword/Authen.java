package apprtc.preeyakamon.hiddinword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Authen extends ActionBarActivity implements View.OnClickListener {

    //Explicit
    private Button signInButton, signUpButton;
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);

        //Setup
        myManage = new MyManage(Authen.this);

        bindWidget();

        buttonController();


    }   // Main Method

    //คือการ เก็บ event จากการคลิก ปุ่ม
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



    }   // checkSignIn

}   // Main Class
