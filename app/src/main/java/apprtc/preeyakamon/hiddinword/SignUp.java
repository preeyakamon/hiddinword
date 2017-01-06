package apprtc.preeyakamon.hiddinword;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends ActionBarActivity {

    //Explicit
    private Button button;
    private EditText nameEditText, userEditText, passwordEditText;
    private String nameString, userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindWidget();

        buttonController();

    }   // Main Method

    private void buttonController() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value From Edit Text
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(SignUp.this);
                    myAlert.myDialog(R.drawable.icon1,
                            getResources().getString(R.string.title_haveSpace),
                            getResources().getString(R.string.message_hameSpace));

                } else {
                    //No Space
                    MyManage myManage = new MyManage(SignUp.this);
                    myManage.addValueToUserTable(nameString, userString, passwordString);
                    Toast.makeText(SignUp.this, "Save User OK", Toast.LENGTH_SHORT).show();
                    finish();

                }   // if

            }   // onClick
        });

    }   // ButtonController

    private void bindWidget() {

        button = (Button) findViewById(R.id.button3);
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);

    }

}   // Main Class
