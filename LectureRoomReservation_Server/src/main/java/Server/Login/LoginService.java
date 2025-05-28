/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Login;

import Controller.login.Login;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user
 */
public class LoginService {
    private Login lg;
    
    public LoginService() throws IOException {
        this.lg = new Login();
    }
    
    public boolean checkUserLogin(String param) {
        List<String> input = List.of(param.split(","));
        boolean result = lg.checkUserLogin(input);
        
        return result;
    }
    
    public String checkUserRole(String param) {
        List<String> input = List.of(param.split(","));
        String result = lg.checkUserRole(input);
        
        return result;
    }
    
    public boolean signUpUser(String param) {
        List<String> input = List.of(param.split(","));
        boolean result = lg.signUpUser(input);
        return result;
    }
}
