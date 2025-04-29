/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;
import db.Excel;
import java.io.IOException;
import java.util.List;
import java.util.*;
//import java.util.ArrayList;
/**
 *
 * @author user
 */
public class UserManagement implements Admin {
    
    private Excel excel;
    private List<List<String>> userInfo; 
    private int userNameIdx;
    private Scanner sc;
    private int userNoticeIdx;
    
    public UserManagement() throws IOException{
        this.excel = new Excel();
        this.userNameIdx = 0;
        this.getUserInformation();
        this.sc = new Scanner(System.in);
        this.userNoticeIdx = 4;
    }
    
    @Override
    public void getUserInformation() {
        this.excel.readUser();
        userInfo = this.excel.getUserInfo();
        
        System.out.println(userInfo);
    }

    @Override
    public void setUserInformation() {
        System.out.print("수정을 원하는 사용자 이름을 입력하세요: ");
        String userName = this.sc.nextLine();
        
        for (List<String> user : this.userInfo) {
             if (userName.equals(user.get(userNameIdx))) {
                 
             }
        }
    }

    @Override
    public void getUserContact() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void checkAnother() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> getUsers() {
        List<String> userList = new ArrayList<>();
        
       for (List<String> user : this.userInfo ) {
           String userName = user.get(userNameIdx);
           
           System.out.println("사용자 이름" + userName);
          userList.add(userName);
       }
       
       return userList;
    }

    @Override
    public void setUserWarning() {
        System.out.print("경고를 줄 사용자를 검색하세요: ");
        String userName = this.sc.nextLine();
        
        for (List<String> userList : this.userInfo) {
            if (userName.equals(userList.get(userNameIdx))) {
                String userNoticeNum = userList.get(this.userNoticeIdx);
                int num = Integer.parseInt(userNoticeNum);
                
                num = num + 1;
                
                userList.set(this.userNoticeIdx, Integer.toString(num));
            }
        }
        System.out.println(this.userInfo);
        // excel 저장할것.
        
        System.out.print("저장하시겠습니까? 저장을 원하시면 y, 원하지 않는다면 n: ");
        String check = this.sc.nextLine();
        
        while (true) {
            switch (check) {
                case "y": 
                    excel.saveUserInfo(this.userInfo);
                    break;
                case "n":
                    break;
                default:
                    System.out.print("y 또는 n을 입력하세요: ");
                    check = this.sc.nextLine();
                    continue;
            }
            break;
        }
    }
    public static void main(String args[]) throws IOException {
        UserManagement admin = new UserManagement();
        //admin.getUserInformation();
       // admin.getUsers();
        admin.setUserWarning();
    }
}
