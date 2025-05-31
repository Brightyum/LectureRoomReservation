/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Login;

import Server.Login.LoginService;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user
 */
public class LoginMessageRouter {

    private LoginService service;
    private int commandIdx;
    private int paramIdx;

    public LoginMessageRouter() throws IOException {
        this.service = new LoginService();
        this.commandIdx = 0;
        this.paramIdx = 1;
    }

    public String judgeCommand(String input) {
        String[] parts = input.split(":", 2);
        String command = parts[this.commandIdx].trim();
        String param = null;

        if (parts.length > 1 && parts[this.paramIdx] != null && !parts[this.paramIdx].trim().isEmpty()) {
            param = parts[this.paramIdx].trim();
        }

        switch (command.toLowerCase()) {
            case "check_login":
                System.out.println("check_login를 동작");
                /*
                boolean result = this.service.checkUserLogin(param);   
                if (result) {
                    return "check_login:success";
                } else {
                    return "check_login:fail";
                }*/
                String[] loginResult = this.service.checkUserLoginWithDetails(param);
                if ("success".equals(loginResult[0])) {
                    String name = loginResult[1];
                    String id = loginResult[2];
                    return "check_login:success:" + name + ":" + id; 
                } else {
                    return "check_login:fail";
                }

            case "check_login_role":
                System.out.println("check_login_role을 동작");
                String roleResult = this.service.checkUserRole(param);
                return "check_login_role:" + roleResult;
            case "signup":
                System.out.println("signup을 동작");
                boolean checkSignUp = this.service.signUpUser(param);
                if (checkSignUp) {
                    return "signup:success";
                } else {
                    return "signup:fail";
                }
            default:
                System.out.println("알 수 없는 응답." + input);
                break;
        }
        return null;
    }

}
