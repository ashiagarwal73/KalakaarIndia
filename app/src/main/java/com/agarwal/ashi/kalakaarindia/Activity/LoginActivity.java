package com.agarwal.ashi.kalakaarindia.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agarwal.ashi.kalakaarindia.Model.User;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
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


import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    EditText phoneNo,otp;
    Button login_button,verify_button;
    TextInputLayout loginTextInputLayout,verifyTextInputLayout;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String TAG="Tag";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNo=findViewById(R.id.phone_number);
        otp=findViewById(R.id.otp);
        login_button=findViewById(R.id.login_button);
        verify_button=findViewById(R.id.verify_button);
        loginTextInputLayout=findViewById(R.id.login_text_input_layout);
        verifyTextInputLayout=findViewById(R.id.verify_text_input_layout);
        progressDialog=new ProgressDialog(this);
    }

    public void onLoginClicked(View view) {
        if(phoneNo.getText().toString().length()!=10)
        {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Sending OTP");
        progressDialog.show();
        mAuth=FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyVerificationCode(code);
                    verify_button.setVisibility(View.GONE);
                    verifyTextInputLayout.setVisibility(View.GONE);
                    otp.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    progressDialog.setMessage("Verifying OTP");
                    progressDialog.show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Cannot verify this Number.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                verify_button.setVisibility(View.VISIBLE);
                verifyTextInputLayout.setVisibility(View.VISIBLE);
                otp.setVisibility(View.VISIBLE);
                phoneNo.setVisibility(View.GONE);
                login_button.setVisibility(View.GONE);
                loginTextInputLayout.setVisibility(View.GONE);
                progressDialog.dismiss();


         }
        };
        String phoneNumber="+91"+phoneNo.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }
    public void onVerifyOtpClicked(View view) {
        if(otp.getText()==null||otp.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        verifyVerificationCode(otp.getText().toString());
        progressDialog.dismiss();
        progressDialog.setMessage("Verifying OTP");
        progressDialog.show();
    }
    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                           final  FirebaseUser user = task.getResult().getUser();
                            progressDialog.dismiss();
                           progressDialog.setMessage("Setting Up Profile");
                            progressDialog.show();
                            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if( dataSnapshot.hasChild(user.getUid()))
                                    {
                                        final User appUser=dataSnapshot.child(user.getUid()).getValue(User.class);
                                        UserDetails.setUser(user);
                                        UserDetails.setAppUser(appUser);
                                        Toast.makeText(LoginActivity.this, "Successfully Registered ", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Intent intent=new Intent(LoginActivity.this,GetUserDetailsActivity.class);
                                        UserDetails.setUser(user);
                                        startActivity(intent);
                                        finish();
                                    }
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();

    }
}
