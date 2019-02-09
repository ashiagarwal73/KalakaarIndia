package com.agarwal.ashi.kalakaarindia.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.agarwal.ashi.kalakaarindia.Model.User;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetUserDetailsActivity extends AppCompatActivity {
    EditText name,email;
    RadioGroup gender;
    Button create_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        create_profile=findViewById(R.id.create_profile);

    }


    public void onCreateProfileClicked(View view) {
        if(name.getText().toString().equals("")||email.getText().toString().equals("")|| gender.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Enter Complete Details", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            final User user=new User();
            user.setName(name.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPhone_number(UserDetails.getUser().getPhoneNumber());
            RadioButton radioButton=findViewById(gender.getCheckedRadioButtonId());
            user.setGender(radioButton.getText().toString());
            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
            if(UserDetails.getUser()!=null) {
                databaseReference.child(UserDetails.getUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            UserDetails.setAppUser(user);
                            Intent intent = new Intent(GetUserDetailsActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }
    }
}
