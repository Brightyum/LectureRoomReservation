/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.LoginServerResponse;

import View.Login.LoginView;
import View.Login.SignUpView;

/**
 *
 * @author user
 */
public class LoginServerResponse {
    private LoginView loginView;
    private SignUpView signUpView;
    private final int idx = 0;
    private final int data = 1;
    
    public LoginServerResponse(LoginView view) {
        this.loginView = view;
    }
    
    public void judgeCommand(String input) {
        String[] parts = input.split(":", 2);
        
        String command = parts[idx].trim();
        String content = parts[data].trim();
        
        switch (command) {
            case "check_login":
                switch (content) {
                    case "success":
                        this.loginView.checkLogin(true);
                        break;
                    case "fail":
                        this.loginView.checkLogin(false);
                        break;
                }
            case "check_login_role":
                this.loginView.checkUserRole(content);
                break;
            case "signup":
                switch (content) {
                    case "success":
                        this.signUpView.checkSignUp(true);
                        break;
                    case "fail":
                        this.signUpView.checkSignUp(false);
                }
        }
    }
    
    public void setSignUpView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }
}
