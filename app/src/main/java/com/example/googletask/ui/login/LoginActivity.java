package com.example.googletask.ui.login;

import android.app.Activity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import android.widget.TextView;
import android.widget.Toast;

import com.example.googletask.MainActivity;
import com.example.googletask.R;
import com.example.googletask.RequestInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ImageButton backButton;
    private Button closeButton;
    private Button promptButton;
    private View Prompt;
    private View Success;
    private View Fail;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private CoordinatorLayout mCoordinatorLayout;
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://docs.google.com/forms/d/e/")
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit =builder.build();
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // hide the title bar
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        link = findViewById(R.id.project_link);
        final Button submitButton = findViewById(R.id.submit);



        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                submitButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getFnameError() != null) {
                    firstName.setError(getString(loginFormState.getFnameError()));

                }
                if (loginFormState.getEmailError() != null) {
                    email.setError(getString(loginFormState.getEmailError()));
                }
                if (loginFormState.getLnameError() != null) {
                   lastName.setError(getString(loginFormState.getLnameError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(firstName.getText().toString(),
                       lastName.getText().toString() , email.getText().toString());
            }
        };
        firstName.addTextChangedListener(afterTextChangedListener);
        email.addTextChangedListener(afterTextChangedListener);
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(firstName.getText().toString(),
                            lastName.getText().toString(), email.getText().toString(), link.getText().toString());
                }
                return false;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.login_layout);
                mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                Prompt = inflater.inflate(R.layout.user_prompt,null);
                mPopupWindow = new PopupWindow(
                        Prompt,1000,1000,true
                );
                closeButton = (Button) Prompt.findViewById(R.id.close_button);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });
                promptButton = (Button) Prompt.findViewById(R.id.prompt_button);
               promptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       executeForm(firstName.getText().toString(),
                               lastName.getText().toString(),
                               email.getText().toString(),
                               link.getText().toString());
                    }
                });
                mPopupWindow.showAtLocation(mCoordinatorLayout, Gravity.CENTER,0,0);

            }
        });
        backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToActivity();
            }
        });
    }

    private void executeForm(String fname ,String lname , String email ,String link){
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.SendData(fname, lname, email, link);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"fail",Toast.LENGTH_SHORT);
            }
        });
    }
    public void backToActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome =  model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}