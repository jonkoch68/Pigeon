package com.syrpro.pigeontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextView signup, login, forgot_pass;
    private Button submit;
    private boolean isSignup = true;
    TextInputLayout pass_confirm_Layout;
    TextInputEditText pass_confirm, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView
        signup = findViewById(R.id.SignUpBtn);
        login = findViewById(R.id.LoginBtn);
        forgot_pass = findViewById(R.id.ForgotPassBtn);
        //Buttons
        submit = findViewById(R.id.SubmitBtn);
        //TextFieldsLayout
        pass_confirm_Layout = findViewById(R.id.EdtConfirmLayout);
        //TextFields
        email = findViewById(R.id.EdtEmail);
        password = findViewById(R.id.EdtPassword);
        pass_confirm = findViewById(R.id.EdtConfirm);

        //Switches form to signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSignup = true;
                signup.setBackground(getResources().getDrawable(R.drawable.login_buttons));
                signup.setTextColor(getColor(R.color.white));
                signup.setElevation(5);

                login.setBackground(getResources().getDrawable(R.drawable.login_unselected_button));
                login.setTextColor(getColor(R.color.dark_blue));
                login.setElevation(0);

                submit.setText("SIGN UP");
                forgot_pass.setVisibility(View.INVISIBLE);
                pass_confirm_Layout.setVisibility(View.VISIBLE);
            }
        });

        //switches form to login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSignup = false;
                login.setBackground(getResources().getDrawable(R.drawable.login_buttons));
                login.setTextColor(getColor(R.color.white));
                login.setElevation(5);

                signup.setBackground(getResources().getDrawable(R.drawable.login_unselected_button));
                signup.setTextColor(getColor(R.color.dark_blue));
                signup.setElevation(0);

                submit.setText("LOG IN");
                forgot_pass.setVisibility(View.VISIBLE);
                pass_confirm_Layout.setVisibility(View.GONE);
            }
        });

        //Opens forgot password page
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Sumbit for items to firebase for creation of authentication
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSignup){
                    //Will send new credentials to sign a user up


                }else{
                    //Will authenticate for a existing user
                }
            }
        });
    }
}