package vn.lvc.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import vn.lvc.quanlychitieu.dao.UserNameAttribute;
import vn.lvc.quanlychitieu.database.DatabaseHelper;

public class Forgot_Activity extends AppCompatActivity {
    List<UserNameAttribute> allUsers;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ;
        findViewById(R.id.resetPassbtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText userNameControl = findViewById(R.id.txtUserName);
                String userName = userNameControl.getText().toString();
                TextInputEditText emailControl = findViewById(R.id.txtEmail);
                String email = emailControl.getText().toString();
                TextInputEditText phoneControl = findViewById(R.id.txtPhoneNumber);
                String phoneNumber = phoneControl.getText().toString();

                UserNameAttribute user = new UserNameAttribute();
                user.userName = userName;
                user.email = email;
                user.phoneNumber = phoneNumber;

                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                allUsers = dbHelper.getAllUsers();

                // Iterate through the list of user attributes
                for (UserNameAttribute userl : allUsers) {
                    // Check if the entered username and password match any user attributes
                    if (userl.userName.equals(userName) && userl.email.equals(email) && userl.phoneNumber.equals(phoneNumber)) {
                        long a = dbHelper.resetUserPass(user);
                        Toast.makeText(Forgot_Activity.this, userName + " new password is 1 ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Forgot_Activity.this, LoginForm.class);
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(Forgot_Activity.this, userName + " input info is incorrect or doesn't exist ", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btbb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                startActivity(intent);
            }
        });
    }
}