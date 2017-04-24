package io.phoenix.demo.rxauthdemo;

import android.text.TextUtils;

import io.phoenix.demo.rxauthdemo.action.AuthAction.SignUpAction;
import io.phoenix.demo.rxauthdemo.result.AuthResult;
import io.reactivex.Observable;

import static io.phoenix.demo.rxauthdemo.result.AuthResult.SignUpResult;


/**
 * Created by yaoda on 24/04/17.
 */

public class AuthManager {
    public Observable<AuthResult.SignUpResult> signUp(SignUpAction action) {

        if (TextUtils.isEmpty(action.getUsername()) || !action.getUsername().contains("@")) {
            return Observable.fromCallable(() -> {
                Thread.sleep(1000);
                return SignUpResult.FAIL_USERNAME;
            });
        }
        if (TextUtils.isEmpty(action.getPassword()) || action.getPassword().length() < 9) {
            return Observable.fromCallable(() -> {
                Thread.sleep(1000);
                return SignUpResult.FAIL_PASSWORD;
            });
        }
        // TODO:  createUser
        return Observable.fromCallable(() -> {
            Thread.sleep(1000);
            return SignUpResult.SUCCESS;
        });
    }

}
