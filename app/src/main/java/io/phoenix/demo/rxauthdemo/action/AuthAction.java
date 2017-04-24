package io.phoenix.demo.rxauthdemo.action;

/**
 * Created by yaoda on 24/04/17.
 */

public class AuthAction {
    public final static class SignUpAction extends AuthAction {
        private final String username;
        private final String password;

        public SignUpAction(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
