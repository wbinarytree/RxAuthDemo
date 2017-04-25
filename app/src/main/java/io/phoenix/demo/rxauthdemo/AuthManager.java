package io.phoenix.demo.rxauthdemo;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import io.phoenix.demo.rxauthdemo.action.AuthAction.SignUpAction;
import io.phoenix.demo.rxauthdemo.result.AuthResult;
import io.reactivex.Observable;

import static io.phoenix.demo.rxauthdemo.result.AuthResult.SignUpResult;


/**
 * Created by yaoda on 24/04/17.
 */

public class AuthManager {
    private SignUpResult result;
    private Observable<SignUpResult> observable = Observable.fromCallable(() -> result)
            //延迟2s发送结果，模拟网络请求延迟
            .delay(5000, TimeUnit.MILLISECONDS);

    public Observable<AuthResult.SignUpResult> signUp(SignUpAction action) {
        //检查用户名是否合法
        if (TextUtils.isEmpty(action.getUsername()) || !action.getUsername().contains("@")) {
            result = SignUpResult.FAIL_USERNAME;
        }
        //检查密码合法
        else if (TextUtils.isEmpty(action.getPassword()) || action.getPassword().length() < 9) {
            result = SignUpResult.FAIL_PASSWORD;
        } else {
            //检查结束，返回注册成功的信息
            // TODO:  createUser
            result = SignUpResult.SUCCESS;
        }
        return observable;
    }
}