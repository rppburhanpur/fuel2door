package com.rppburhanpur.fuel2door;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class User_Registration extends AppCompatActivity {

    private EditText phonenumber, otpedittext;
    private Button buttonotp,loginbutton;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    private String mverificationID;

    private Button english_button, hindi_button;
    private String IS_CURRENT_ENGLISH = "YES";

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logreg_screen);

        phonenumber = (EditText) findViewById(R.id.mobilenumberet);
        buttonotp  = (Button) findViewById(R.id.sendotpbtn);

        otpedittext = (EditText) findViewById(R.id.otpet);
        loginbutton = (Button) findViewById(R.id.loginbtn);
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = this.getSharedPreferences(IS_CURRENT_ENGLISH,MODE_PRIVATE);
        sharedPreferencesEditor =sharedPreferences.edit();

        FirebaseOptions firebaseOptions = FirebaseOptions.fromResource(this);
        FirebaseOptions.Builder  builder = new FirebaseOptions.Builder();

        //Language button initialization
        english_button = (Button) findViewById(R.id.English);
        hindi_button = (Button) findViewById(R.id.Hindi);


        relativeLayout = (RelativeLayout) findViewById(R.id.hiddenRELE);
        english_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("en");
//                Intent intent = new Intent(User_Registration.this,User_Registration.class);
//                startActivity(intent);
            }
        });

        hindi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("hi");
//                Intent intent = new Intent(User_Registration.this,User_Registration.class);
//                startActivity(intent);
            }
        });

        buttonotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phonenumber.getText().toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("+91");
                stringBuilder.append(phone);
                Toast.makeText(User_Registration.this, "number is " + stringBuilder.toString() , Toast.LENGTH_SHORT).show();
                sendforgetverificationcode(stringBuilder.toString());
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otpedittext.getText().toString();
                Toast.makeText(User_Registration.this, "inserted by you : "+code, Toast.LENGTH_SHORT).show();
                putotp(code);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(User_Registration.this, "verification successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(User_Registration.this, "verication unsuccessful", Toast.LENGTH_SHORT).show();
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(User_Registration.this, "" + e , Toast.LENGTH_SHORT).show();
                    if(!phonenumber.getText().toString().startsWith("+91")){
                        phonenumber.setError("invalid number, Number must be start with +91");
                    }
                }
                if(e instanceof FirebaseTooManyRequestsException){
                    Toast.makeText(User_Registration.this, "Too many attempts from this device.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mverificationID = s;
            }
        };

    }

    public void sendforgetverificationcode(String phone){
        PhoneAuthProvider.getInstance(mAuth).verifyPhoneNumber(phone,60, TimeUnit.SECONDS,this,mCallbacks);
    }

    public void putotp(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mverificationID,code);
        signwithauth(credential);
    }

    public void signwithauth(final PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(User_Registration.this, "login successsful", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void setLanguage(String language){
            Locale locale = new Locale(language);
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            resources.updateConfiguration(configuration,displayMetrics);
            Toast.makeText(User_Registration.this, "Language Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(User_Registration.this,User_Registration.class);
            startActivity(intent);
            finish();
    }
//    private void setCurrentLanguage(boolean currentLanguage){
//        sharedPreferencesEditor.putBoolean(IS_CURRENT_ENGLISH,currentLanguage);
//        sharedPreferencesEditor.commit();
//    }
//    private boolean isCurrent_English(){
//        return sharedPreferences.getBoolean(IS_CURRENT_ENGLISH,false);
//}
}








//------------------------------- Important comment code don't remove it---------------------------------------



//                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                   String phoneNUM = firebaseUser.getPhoneNumber();
//                    sharedPreferencesEditor.putString("uid",phoneNUM);
//                    sharedPreferencesEditor.apply();
//                    sharedPreferencesEditor.commit();

//                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.hasChild(phonenumber.getText().toString())){
//                            Toast.makeText(User_Registration.this, "number exit", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(User_Registration.this, "does not exit", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


//         firebaseUser = mAuth.getCurrentUser();
//        Toast.makeText(this, ""+firebaseUser.getPhoneNumber()+"  and UID "+ firebaseUser.getUid(), Toast.LENGTH_LONG).show();
//
//        editor = getSharedPreferences("name",MODE_PRIVATE).edit();


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("Users");
//



//    private SharedPreferences.Editor editor;
//
//    private FirebaseUser firebaseUser;