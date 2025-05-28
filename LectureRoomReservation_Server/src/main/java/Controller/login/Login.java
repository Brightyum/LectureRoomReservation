/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.login;

import Model.Excel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Login {
    private Excel excel;
    private List<List<String>> userInfo;
    private int userIdIdx;
    private int userPwdIdx;
    private int userRoleIdx;
    
    public Login() throws IOException {
        this.excel = new Excel();
        this.userIdIdx = 1;
        this.userPwdIdx = 2;
        this.userRoleIdx = 7;
    }
    
    public void getUserInformation() {
        this.excel.readUser();
        userInfo = this.excel.getUserInfo();
        
        System.out.println(userInfo);
    }
    
    public List<String> getUserId() {
        this.getUserInformation();
        
        List<String> userId = new ArrayList<>();
        for (List<String> user : userInfo) {
            userId.add(user.get(userIdIdx));
        }
        
        return userId;
    }
    
    public List<String> getUserPwd() {
        this.getUserInformation();
        
        List<String> userPwd = new ArrayList<>();
        for (List<String> user : userInfo) {
            userPwd.add(user.get(userPwdIdx));
        }
        
        return userPwd;
    }
    
    public List<String> getUserRole() {
        this.getUserInformation();
        
        List<String> userRole = new ArrayList<>();
        for (List<String> user : userInfo) {
            userRole.add(user.get(userRoleIdx));
        }
        
        return userRole;
    }
    
    public boolean checkUserLogin(List<String> input) {
        int idIdx = 0;
        int pwdIdx = 1;
        
        String id = input.get(idIdx);
        String pwd = input.get(pwdIdx);
        
        List<String> userId = this.getUserId();
        List<String> userPwd = this.getUserPwd();
        
        for (int i = 0; i < userId.size(); i++) {
              if (id.equals(userId.get(i)) && pwd.equals((userPwd.get(i)))) {
                  return true;
              }
        }
        
        return false;
    }   
    
    public String checkUserRole(List<String> input) {
        int idIdx = 0;
        int pwdIdx = 1;
        
        String id = input.get(idIdx);
        String pwd = input.get(pwdIdx);
        
        List<String> userId = this.getUserId();
        List<String> userPwd = this.getUserPwd();
        List<String> userRole = this.getUserRole();
        
        for (int i = 0; i < userId.size(); i++) {
              if (id.equals(userId.get(i)) && pwd.equals((userPwd.get(i)))) {
                  return userRole.get(i);
              }
        }
        return null;
    }
    
    public boolean signUpUser(List<String> input) {
        String inputId = input.get(this.userIdIdx);
        List<String> userId = this.getUserId();
        
        for (String idData : userId) {
            if (inputId.equals(idData)) {
                return false;
            }
        }
        
        int warnIdx = 4;
        int questionIdx = 5;
        int solveIdx = 6;
        
        List<String> result = new ArrayList<>(input);
        result.add(warnIdx, "0");
        result.add(questionIdx, "-");
        result.add(solveIdx, "0");
        
        try {
            this.getUserInformation();
            this.userInfo.add(result);
            this.excel.saveUserInfo(this.userInfo);
            System.out.println("저장에 성공했습니다");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String args[]) throws IOException {
        Login login = new Login();
        String id = "123";
        String pwd = "123";
        
        List<String> data = new ArrayList<>();
        data.add(id);
        data.add(pwd);
        if (login.checkUserLogin(data)) {
            System.out.println("성공");
        }
    }
}
