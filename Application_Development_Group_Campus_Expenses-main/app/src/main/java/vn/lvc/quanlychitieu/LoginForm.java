package vn.lvc.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import vn.lvc.quanlychitieu.dao.UserNameAttribute;
import vn.lvc.quanlychitieu.database.DatabaseHelper;

public class LoginForm extends AppCompatActivity {

    List<UserNameAttribute> allUsers;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        Button openRegisterActivity = findViewById(R.id.RegisterBtt);

        findViewById(R.id.LoginBtt).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                // Retrieve the entered username and password from your input fields
                TextInputEditText enteredUsername = findViewById(R.id.userName_txt);
                String userName = enteredUsername.getText().toString();
                TextInputEditText enteredPassword = findViewById(R.id.password_txt);
                String password = enteredPassword.getText().toString();

                // Call the CheckUser method to retrieve the list of user attributes
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                allUsers = dbHelper.getAllUsers();

                // Iterate through the list of user attributes
                for (UserNameAttribute user : allUsers) {
                    // Check if the entered username and password match any user attributes
                    if (user.userName.equals(userName) && user.password.equals(password)) {

                        MainActivity.userName = user.userName;
                        MainActivity.userEmail = user.email;
                        MainActivity.userPhoneNumber = user.phoneNumber;

                        Toast.makeText(LoginForm.this, "Hello " + userName, Toast.LENGTH_SHORT).show();

                        // Perform the desired action, e.g., start a new activity
                        Intent intent = new Intent(LoginForm.this, MainActivity.class);
                        startActivity(intent);
                        return;
                    }
                }

                // Invalid username or password
                // Display an error message
                Toast.makeText(LoginForm.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        openRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                allUsers = dbHelper.getAllUsers();
                if (allUsers.isEmpty()) {
                    Intent intent = new Intent(LoginForm.this, RegisterForm.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginForm.this, "Login to create new account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.forgotPassBtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Forgot_Activity.class);
                startActivity(intent);
            }
        });
    }
}