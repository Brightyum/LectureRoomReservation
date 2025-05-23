/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Admin;
import admin.UserManagement;
import db.Excel;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 *
 * @author user
 */
public class AdminService {
    private UserManagement um;
    
    public AdminService() {
        this.um = new UserManagement();
    }
    
    public String getUsers() {
        List<String> names = um.getUsers();
        String result = String.join(",", names);
        return result;
    }
    
    public String getId() {
        List<String> ids = um.getId();
        String result = String.join(",", ids);
        return result;
    }
    
    public boolean removeUser(String id) {
        boolean result = um.removeUser(id);
        return result;
    }
    
    public String getUserDetail(String userId) {
        List<String> userList = um.getUserDetail(userId);
        String result = String.join(",", userList);
        return result;
    }
    
    public boolean setUserInformation(List<String> list, String id) {
        boolean result = um.setUserInformation(list, id);
        return result;
    }
    
    public boolean setUserWarning(String id) {
        boolean result = um.setUserWarning(id);
        return result;
    }
    
    public boolean getIdCheck(String id) {
        boolean result = um.getIdCheck(id);
        return result;
    }
    
    public boolean addUser(List<String> newUser) {
        boolean result = um.addUser(newUser);
        return result;
    }
}
