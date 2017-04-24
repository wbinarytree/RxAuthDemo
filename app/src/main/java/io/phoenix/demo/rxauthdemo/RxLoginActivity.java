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
import io.phoenix.demo.rxauthdemo.event.AuthEvent;
import io.phoenix.demo.rxauthdemo.event.AuthEvent.SignUpEvent;
import io.phoenix.demo.rxauthdemo.translator.AuthTranslator;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        translator = new AuthTranslator(new AuthManager());
        ButterKnife.bind(this);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposables.add(RxView.clicks(mEmailSignInButton)
                              .map(ignore -> new SignUpEvent(mEmailView.getText().toString(), mPasswordView.getText().toString()))
                              .compose(translator.signUp)
                              .observeOn(AndroidSchedulers.mainThread())
                              .subscribe(authUiModel -> {
                                  Log.d("", "onStart:received " );
                                  if(authUiModel.isInProcess()){
                                      Log.d("", "onStart: in progress " + System.currentTimeMillis());
                                  }
                                  mProgressView.setVisibility(authUiModel.isInProcess() ? View.VISIBLE : View.GONE);
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
                                  if(authUiModel.isSuccess()){
                                      Log.d("", "onStart: in success " + System.currentTimeMillis());
                                      Toast.makeText(this, "CreateUser SuccessFull", Toast.LENGTH_SHORT)
                                           .show();
                                  }
                              }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
