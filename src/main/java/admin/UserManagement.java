/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;
import db.Excel;
import java.io.IOException;
import java.util.List;
//import java.util.ArrayList;
/**
 *
 * @author user
 */
public class UserManagement implements Admin {
    
    private Excel excel;
   
    private List<List<String>> userList; 
    public UserManagement() throws IOException{
        this.excel = new Excel();
    }
    
    @Override
    public void getUserInformation() {
        this.excel.readUser();
        userList = this.excel.getUserInfo();
        
        System.out.print(userList);
    }

    @Override
    public void setUserInformation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public void getUsers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setUserWarning() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public static void main(String args[]) throws IOException {
        UserManagement admin = new UserManagement();
        admin.getUserInformation();
    }
}
