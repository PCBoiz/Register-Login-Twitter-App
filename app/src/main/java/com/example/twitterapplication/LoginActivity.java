package com.example.twitterapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailInput, passwordInput;
    private MaterialCheckBox rememberMeCheckbox;
    private MaterialButton loginButton, registerButton, forgotPasswordButton;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupSharedPreferences();
        setupTextWatchers();
        setupClickListeners();
    }

    private void initializeViews() {
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
        if (rememberMe) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            emailInput.setText(savedEmail);
            rememberMeCheckbox.setChecked(true);
        }
    }

    private void setupTextWatchers() {
        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateEmail();
            }
        });

        passwordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validatePassword();
            }
        });
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> attemptLogin());

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        forgotPasswordButton.setOnClickListener(v -> handleForgotPassword());
    }

    private boolean validateEmail() {
        String email = emailInput.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Please enter a valid email address");
            return false;
        }
        emailLayout.setError(null);
        return true;
    }

    private boolean validatePassword() {
        String password = passwordInput.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            return false;
        } else if (password.length() < 6) {
            passwordLayout.setError("Password must be at least 6 characters");
            return false;
        }
        passwordLayout.setError(null);
        return true;
    }

    private void attemptLogin() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        // TODO: Implement actual API authentication
        // For now, we'll just simulate a successful login
        handleSuccessfulLogin(email);
    }

    private void handleSuccessfulLogin(String email) {
        if (rememberMeCheckbox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putBoolean(KEY_REMEMBER_ME, true);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }

        // TODO: Navigate to main activity
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
    }

    private void handleForgotPassword() {
        String email = emailInput.getText().toString().trim();
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // TODO: Implement password recovery logic
            Toast.makeText(this, "Password reset instructions sent to your email", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
    }
}
