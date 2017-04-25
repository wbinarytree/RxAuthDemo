package io.phoenix.demo.rxauthdemo.translator;


import io.phoenix.demo.rxauthdemo.AuthManager;
import io.phoenix.demo.rxauthdemo.AuthUiModel;
import io.phoenix.demo.rxauthdemo.action.AuthAction.SignUpAction;
import io.phoenix.demo.rxauthdemo.event.AuthEvent.SignUpEvent;
import io.phoenix.demo.rxauthdemo.result.AuthResult.SignUpResult;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Created by yaoda on 24/04/17.
 */

public class AuthTranslator {
    private AuthManager authManager;
    private Subject<SignUpEvent> middle = PublishSubject.create();
    private Observable<AuthUiModel> authUiModelObservable
            = middle.map(event -> new SignUpAction(event.getUsername(), event.getPassword()))
            //使用FlatMap转向，进行注册
            .flatMap(action -> authManager.signUp(action)
                    //扫描结果
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
                    //设置初始状态为loading。
                    .startWith(AuthUiModel.inProcess())
                    //设置错误状态为error，防止触发onError() 造成断流
                    .onErrorReturn(error -> AuthUiModel.fail(true, true, error.getMessage())))
            .replay(1)
            .autoConnect();

    public final ObservableTransformer<SignUpEvent, AuthUiModel> signUp
            //上游是UiEvent，封装成对应的Action
            = observable -> {
        //中间人切换监听
        observable.subscribe(middle);
        return authUiModelObservable;
    };


    public AuthTranslator(AuthManager authManager) {
        this.authManager = authManager;
    }
}
