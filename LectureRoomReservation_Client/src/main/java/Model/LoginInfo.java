/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author leeseungmin
 */
public class LoginInfo {
    private static String userId;
    private static String userName;

    public static void setUser(String id, String name) {
        userId = id;
        userName = name;
    }
    public static String getUserId() { return userId; }
    public static String getUserName() { return userName; }
}