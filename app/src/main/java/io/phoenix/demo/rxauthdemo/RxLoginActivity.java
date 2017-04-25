package io.phoenix.demo.rxauthdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.phoenix.demo.rxauthdemo.event.AuthEvent.SignUpEvent;
import io.phoenix.demo.rxauthdemo.translator.AuthTranslator;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by awang on 2017/4/23.
 */

public class RxLoginActivity extends AppCompatActivity {
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.login_progress)
    View mProgressView;
    @BindView(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    private CompositeDisposable disposables;
    private AuthTranslator translator;
    private Observable<String> stringObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        translator = ((MainApplication) getApplication()).getTranslator();
        ButterKnife.bind(this);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Observable<SignUpEvent> click = RxView.clicks(mEmailSignInButton)
                .map(ignore -> new SignUpEvent(mEmailView.getText()
                        .toString(), mPasswordView.getText().toString()));

        disposables.add(click.compose(translator.signUp)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authUiModel -> {
                    //载入进度条
                    mProgressView.setVisibility(authUiModel.isInProcess() ? View.VISIBLE : View.GONE);
                    //判断用户名/密码是否合法
                    if (!authUiModel.isPwdValidate()) {
                        mPasswordView.setError(authUiModel.getErrorMessage());
                    } else {
                        mPasswordView.setError(null);
                    }
                    if (!authUiModel.isUsrValidate()) {
                        mEmailView.setError(authUiModel.getErrorMessage());
                    } else {
                        mEmailView.setError(null);
                    }
                    //是否成功
                    if (authUiModel.isSuccess()) {
                        Toast.makeText(this, "CreateUser SuccessFull", Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

}
