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

    public String[] checkUserLoginWithDetails(String param) {
        List<String> input = List.of(param.split(","));
        boolean isValid = lg.checkUserLogin(input);

        if (isValid) {
            List<String> userIds = lg.getUserId();
            List<String> userPwds = lg.getUserPwd();
            List<List<String>> userInfo = lg.getUserInfo();

            String id = input.get(0);
            String pwd = input.get(1);
            for (int i = 0; i < userIds.size(); i++) {
                if (id.equals(userIds.get(i)) && pwd.equals(userPwds.get(i))) {
                    String name = userInfo.get(i).get(0); 
                    return new String[]{"success", name, id};
                }
            }
        }
        return new String[]{"fail"};
    }
}
