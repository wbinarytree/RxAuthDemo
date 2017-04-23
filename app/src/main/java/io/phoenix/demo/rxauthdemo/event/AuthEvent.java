package io.phoenix.demo.rxauthdemo.event;

/**
 * Created by awang on 2017/4/23.
 */

public class AuthEvent {
    public final static class SignUpEvent extends AuthEvent {
        private final String username;
        private final String password;

        public SignUpEvent(String username, String password) {
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

    public final static class CheckEvent extends AuthEvent {
        private final String username;
        private final String password;

        public CheckEvent(String username, String password) {
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
