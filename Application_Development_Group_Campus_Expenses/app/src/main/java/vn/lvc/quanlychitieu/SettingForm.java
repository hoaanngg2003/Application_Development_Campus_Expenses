package vn.lvc.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import vn.lvc.quanlychitieu.dao.UserNameAttribute;
import vn.lvc.quanlychitieu.database.DatabaseHelper;

public class SettingForm extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_form);
        TextInputEditText userNameControl = findViewById(R.id.txtUserName);
        TextInputEditText  emailControl = findViewById(R.id.txtEmail);
        TextInputEditText phoneControl = findViewById(R.id.txtPhoneNumber);

        userNameControl.setText(MainActivity.userName);
        emailControl.setText(MainActivity.userEmail);
        phoneControl.setText(MainActivity.userPhoneNumber);

        findViewById(R.id.btbb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.changeInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterForm.class);
                intent.putExtra("mode", 1);
                startActivity(intent);
            }
        });

        findViewById(R.id.addAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterForm.class);
                intent.putExtra("mode", 0);
                startActivity(intent);
            }
        });

        findViewById(R.id.removeAcc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingForm.this, "Hold for 10 second to delete", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.removeAcc).setOnTouchListener(new View.OnTouchListener() {

            private Handler handler;
            private Runnable runnable;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Create a new Handler and Runnable when the button is initially pressed
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                                int a = dbHelper.deleteUser();
                                Toast.makeText(SettingForm.this, "Successfully Deleted " + MainActivity.userName, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                                startActivity(intent);
                            }
                        };

                        // Delay the execution of the runnable by 10 seconds
                        handler.postDelayed(runnable, 10000);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Remove any pending callbacks when the button is released
                        if (handler != null && runnable != null) {
                            handler.removeCallbacks(runnable);
                        }
                        break;
                }
                return false;
            }
        });
    }
}