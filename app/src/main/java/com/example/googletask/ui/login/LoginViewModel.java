package com.example.googletask.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.googletask.data.LoginRepository;
import com.example.googletask.data.Result;
import com.example.googletask.data.model.LoggedInUser;
import com.example.googletask.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String fname, String lname,String email,String link) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(fname, lname , email, link);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String fname,String lname, String email) {
        if (!isFirstNameValid(fname)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_fname, null,null));
        } else if (!isLastNameValid(lname)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_lname,null));
        }else if(!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(null,R.string.invalid_email,null));
        }

        else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isFirstNameValid(String fname) {
        if (fname == null) {
            return false;
        }
        return true;
    }
    private boolean isLastNameValid(String lname) {
        if (lname == null) {
            return false;
        }
        return true;
    }
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }
}