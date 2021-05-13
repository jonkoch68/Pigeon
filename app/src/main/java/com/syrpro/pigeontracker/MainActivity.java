package com.syrpro.pigeontracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //UI Components
    private TextView signup, login, forgot_pass;
    private Button submit;
    private boolean isSignup = true;
    TextInputLayout pass_confirm_Layout;
    TextInputEditText pass_confirm, email, password;

    static final String USER_PATH = "user/";

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
                System.out.println("Clicked");
                Toast.makeText(MainActivity.this, "Forgot Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //Sumbit for items to firebase for creation of authentication
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Error: Please fill in empty field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isSignup && !pass_confirm.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Error: Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isSignup){
                    //Will send new credentials to sign a user up
                    handleSignup();
                    return;
                }else{
                    //Will authenticate for a existing user
                    handleLogin();
                    return;
                }
            }
        });
    }

    public void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    Toast.makeText(MainActivity.this, "Login in successful!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void handleSignup(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Map<String,String> info = new HashMap<>();
                    info.put("Email", email.getText().toString());
                    info.put("Name", "");
                    info.put("Address", "");
                    info.put("City", "");
                    info.put("State","");
                    FirebaseFirestore.getInstance().collection(USER_PATH).document(uID).set(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Account sign up successful!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }else{
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}