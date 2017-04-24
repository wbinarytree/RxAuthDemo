package io.phoenix.demo.rxauthdemo.translator;


import io.phoenix.demo.rxauthdemo.AuthManager;
import io.phoenix.demo.rxauthdemo.AuthUiModel;
import io.phoenix.demo.rxauthdemo.action.AuthAction.SignUpAction;
import io.phoenix.demo.rxauthdemo.event.AuthEvent.SignUpEvent;
import io.phoenix.demo.rxauthdemo.result.AuthResult.SignUpResult;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yaoda on 24/04/17.
 */

public class AuthTranslator {
    private AuthManager authManager;
    public final ObservableTransformer<SignUpEvent, AuthUiModel> signUp
            = observable -> observable.map(event -> new SignUpAction(event.getUsername(), event.getPassword()))
                                      .flatMap(action -> authManager.signUp(action)
                                                                    .subscribeOn(Schedulers.io())
                                                                    .map(signUpResult -> {
                                                                        if (signUpResult == SignUpResult.FAIL_USERNAME) {
                                                                            return AuthUiModel.fail(false, true, "Username error");
                                                                        }
                                                                        if (signUpResult == SignUpResult.FAIL_PASSWORD) {
                                                                            return AuthUiModel.fail(true, false, "Password error");
                                                                        }
                                                                        if (signUpResult == SignUpResult.SUCCESS) {
                                                                            return AuthUiModel.success();
                                                                        }
                                                                        //TODO Handle error
                                                                        throw new IllegalArgumentException("Unknown Result");
                                                                    })
                                                                    .startWith(AuthUiModel.inProcess())
                                                                    .onErrorReturn(error -> AuthUiModel
                                                                            .fail(true, true, error.getMessage())));


    public AuthTranslator(AuthManager authManager) {
        this.authManager = authManager;
    }


}
