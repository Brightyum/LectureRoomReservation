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
    private int userIdIdx;
    private int userPwdIdx;
    private int userMajorIdx;
    
    
    public UserManagement() {
        this(createDefaultExcel(), new Scanner(System.in));
    }
    
    public UserManagement(Excel excel, Scanner sc) {
        this.excel = excel;
        this.userNameIdx = 0;
        this.userNoticeIdx = 4;
        this.sc = sc;
        this.getUserInformation();
        this.userIdIdx = 1;
        this.userPwdIdx = 2;
        this.userMajorIdx = 3;
        
    }
    
    private static Excel createDefaultExcel() {
        try {
            return new Excel();
        } catch (IOException ex) {
            System.out.println("Excel 생성 실패" + ex);
            return null;
        }
    }
    
    public void getUserInformation() {
        this.excel.readUser();
        userInfo = this.excel.getUserInfo();
        
        System.out.println(userInfo);
    }
    
    //@Override
    public void setUserInformation(List<String> list, String id) {
        for (List<String> user : this.userInfo) {
             if (id.equals(user.get(this.userIdIdx))) {
                 int listNameIdx = 0;
                 int listPwdIdx = 1;
                 int listMajorIdx = 2;
                 user.set(this.userNameIdx, list.get(listNameIdx));
                 user.set(this.userPwdIdx, list.get(listPwdIdx));
                 user.set(this.userMajorIdx, list.get(listMajorIdx));
                 System.out.println(user);
            }
        }
        //저장 하는 코드를 구성
        excel.saveUserInfo(this.userInfo);
    }

    @Override
    public void getUserContact() {
       int problemIdx = 5;
       int problemCheckIdx = 6;
       String check = "0";
       
       for (List<String> user : this.userInfo) {
           if (check.equals(user.get(problemCheckIdx))) {
               System.out.println(user.get(problemIdx));
           }
       }
    }

    @Override
    public void checkAnother() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<String> getUsers() {
        List<String> userNameList = new ArrayList<>();
        
       for (List<String> user : this.userInfo ) {
           
           String userName = user.get(this.userNameIdx);
           String userId = user.get(this.userIdIdx);
           
           System.out.println("사용자 이름" + userName + "사용자 ID: " + userId);
           userNameList.add(userName);
       }
       
       return userNameList;
    }
    
    public List<String> getId() {
        
        List<String> userIdList = new ArrayList<>();
        
        for (List<String> user : this.userInfo ) {
            
            String userId = user.get(this.userIdIdx);

            System.out.println("사용자 ID: " + userId);
            userIdList.add(userId);
        }
        
        return userIdList;    
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
    
    public List<String> getUserDetail(String userId) {
        
        for (List<String> userList : this.userInfo ) {
           if (userId.equals(userList.get(userIdIdx))) {
               return userList;
           } 
       }
       return null;
    }
    public static void main(String args[]) throws IOException {
        UserManagement admin = new UserManagement();
        //admin.getUserInformation();
       // admin.getUsers();
        //admin.setUserWarning();
        admin.getUserContact();
    }

    @Override
    public void setUserInformation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
