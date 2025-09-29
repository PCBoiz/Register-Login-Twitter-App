package com.example.twitterapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    private TextInputEditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private MaterialButton registerButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setupTextWatchers();
        setupClickListeners();
    }

    private void initializeViews() {
        usernameLayout = findViewById(R.id.usernameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);

        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
    }

    private void setupTextWatchers() {
        usernameInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateUsername();
            }
        });

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

        confirmPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateConfirmPassword();
            }
        });
    }

    private void setupClickListeners() {
        registerButton.setOnClickListener(v -> attemptRegistration());

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateUsername() {
        String username = usernameInput.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("Username is required");
            return false;
        } else if (username.length() < 3) {
            usernameLayout.setError("Username must be at least 3 characters");
            return false;
        }
        usernameLayout.setError(null);
        return true;
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
        } else if (!password.matches(".*[A-Z].*")) {
            passwordLayout.setError("Password must contain at least one uppercase letter");
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            passwordLayout.setError("Password must contain at least one lowercase letter");
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            passwordLayout.setError("Password must contain at least one number");
            return false;
        }
        passwordLayout.setError(null);
        return true;
    }

    private boolean validateConfirmPassword() {
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Please confirm your password");
            return false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
            return false;
        }
        confirmPasswordLayout.setError(null);
        return true;
    }

    private void attemptRegistration() {
        if (!validateUsername() || !validateEmail() || !validatePassword() || !validateConfirmPassword()) {
            return;
        }

        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        // TODO: Implement actual API registration
        // For now, we'll just simulate a successful registration
        handleSuccessfulRegistration();
    }

    private void handleSuccessfulRegistration() {
        Toast.makeText(this, "Registration successful! Please check your email for verification.", Toast.LENGTH_LONG).show();

        // Navigate to login screen
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
