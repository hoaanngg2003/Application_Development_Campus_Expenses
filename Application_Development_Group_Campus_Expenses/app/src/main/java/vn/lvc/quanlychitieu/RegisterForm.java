package vn.lvc.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import vn.lvc.quanlychitieu.dao.UserNameAttribute;
import vn.lvc.quanlychitieu.database.DatabaseHelper;


public class RegisterForm extends AppCompatActivity {
    static boolean error;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);

        Button btt = findViewById(R.id.Registerbtt);
        TextView label = findViewById(R.id.txtBoxCAndE);
        TextInputEditText userNameControl = findViewById(R.id.txtUserName);
        TextInputEditText emailControl = findViewById(R.id.txtEmail);
        TextInputEditText phoneControl = findViewById(R.id.txtPhoneNumber);
        if (mode == 1){
            label.setText("Edit Account");
            btt.setText("Edit");
            userNameControl.setText(MainActivity.userName);
            emailControl.setText(MainActivity.userEmail);
            phoneControl.setText(MainActivity.userPhoneNumber);
        } else if (mode == 0) {
            label.setText("Register Account");
            btt.setText("Register");
        }

        findViewById(R.id.Registerbtt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText userNameControl = findViewById(R.id.txtUserName);
                String userName = userNameControl.getText().toString();
                TextInputEditText  emailControl = findViewById(R.id.txtEmail);
                String email  = emailControl.getText().toString();
                TextInputEditText phoneControl = findViewById(R.id.txtPhoneNumber);
                String phoneNumber = phoneControl.getText().toString();
                TextInputEditText passwordControl = findViewById(R.id.txtPassword);
                String password = passwordControl.getText().toString();
                TextInputEditText rePasswordControl = findViewById(R.id.txtRePassword);
                String rePassword = rePasswordControl.getText().toString();

                UserNameAttribute user = new UserNameAttribute();
                user.userName = userName;
                user.email = email;
                user.phoneNumber = phoneNumber;
                user.password =password;
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

                // Call the CheckUser method to retrieve the list of user attributes;
                List<UserNameAttribute> allUsers = dbHelper.getAllUsers();
                if (userName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterForm.this, "Must input all the field", Toast.LENGTH_SHORT).show();
                }
                else if (!email.contains("@")){
                    Toast.makeText(RegisterForm.this, "Pls input actual mail", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(rePassword)){
                    Toast.makeText(RegisterForm.this, "Password is not = Repassword", Toast.LENGTH_SHORT).show();
                }
                else {
                    error = false;
                    for (UserNameAttribute userl : allUsers) {
                        // Check if the entered username and password match any user attributes
                        if (userl.userName.equals(userName) & mode == 0) {
                            Toast.makeText(RegisterForm.this, "This username already being used, choose another one", Toast.LENGTH_SHORT).show();
                            error = true;
                            break;
                        } else if (userl.userName.equals(MainActivity.userName) & mode == 1) {
                            dbHelper.changeUser(user);
                            Toast.makeText(RegisterForm.this, "Successfully Changed to " + userName, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                            startActivity(intent);
                            return;
                        }
                    }
                    if (!error & mode == 0) {
                        dbHelper.insertUser(user);
                        Toast.makeText(RegisterForm.this, "Successfully created " + userName, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegisterForm.this, "Something went wrong, pls try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        findViewById(R.id.btbb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode == 1){
                    Intent intent = new Intent(getApplicationContext(), SettingForm.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                    startActivity(intent);
                }
            }
        });

    }
}