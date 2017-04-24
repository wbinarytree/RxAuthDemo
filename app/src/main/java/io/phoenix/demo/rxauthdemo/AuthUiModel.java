package io.phoenix.demo.rxauthdemo;

/**
 * Created by yaoda on 24/04/17.
 */

public class AuthUiModel {
    private final boolean inProcess;
    private final boolean usrValidate;
    private final boolean pwdValidate;
    private final boolean success;
    private final String errorMessage;

    private AuthUiModel(boolean inProcess, boolean usrValidate, boolean pwdValidate, boolean success, String errorMessage) {
        this.inProcess = inProcess;
        this.usrValidate = usrValidate;
        this.pwdValidate = pwdValidate;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static AuthUiModel idle() {
        return new AuthUiModel(false, true, true, false, "");
    }

    public static AuthUiModel inProcess() {
        return new AuthUiModel(true, true, true, false, "");
    }

    public static AuthUiModel success() {
        return new AuthUiModel(false, true, true, true, "");
    }

    public static AuthUiModel fail(boolean username, boolean password, String msg) {
        return new AuthUiModel(false, username, password, false, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public boolean isUsrValidate() {
        return usrValidate;
    }

    public boolean isPwdValidate() {
        return pwdValidate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
