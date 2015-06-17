package org.vcanpay.eo;

/**
 * Created by patrick wai on 2015/6/16.
 */
public class Login {
    private String emailInput;
    private String pwdInput;

    public Login(String emailInput, String pwdInput) {
        this.emailInput = emailInput;
        this.pwdInput = pwdInput;
    }

    public String getEmailInput() {
        return emailInput;
    }

    public void setEmailInput(String emailInput) {
        this.emailInput = emailInput;
    }

    public String getPwdInput() {
        return pwdInput;
    }

    public void setPwdInput(String pwdInput) {
        this.pwdInput = pwdInput;
    }
}
