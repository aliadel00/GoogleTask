package com.example.googletask.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer fnameError;
    @Nullable
    private Integer lnameError;
    @Nullable
    private Integer emailError;

    private boolean isDataValid;

    LoginFormState(@Nullable Integer fnameError, @Nullable Integer emailError,@Nullable Integer lnameError) {
        this.fnameError = fnameError;
        this.emailError = emailError;
        this.lnameError = lnameError;

        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.fnameError = null;
        this.emailError = null;
        this.lnameError = null;

        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getLnameError() {
        return lnameError;
    }

    @Nullable
    Integer getFnameError() {
        return fnameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }



    boolean isDataValid() {
        return isDataValid;
    }
}