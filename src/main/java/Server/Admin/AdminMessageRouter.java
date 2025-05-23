/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Admin;

import java.util.List;

/**
 *
 * @author user
 * 
 */
public class AdminMessageRouter {
    private AdminService service;
    private int commandIdx;
    private int paramIdx;
    
    public AdminMessageRouter() {
        this.service = new AdminService();
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
        
        switch  (command.toLowerCase()) {
            case "get_user_names":
                System.out.println("get_user_names를 동작하겠습니다.");
                return "get_user_names:" + this.service.getUsers();
                
            case "get_user_ids":
                System.out.println("get_user_ids를 동작하겠습니다.");
                return "get_user_ids:" + this.service.getId();
                
            case "remove_user":
                System.out.println("remove_user을 동작하겠습니다");
                boolean result = this.service.removeUser(param);
                if (result) {
                    return "remove_user:success";
                } else {
                    return "remove_user:fail";
                }
                
            case "get_user_detail":
                System.out.println("get_user_detail을 동작하겠습니다");
                System.out.println("get_user_detail:" + this.service.getUserDetail(param));
                return "get_user_detail:" + this.service.getUserDetail(param);
                
            case "set_user_info":
                String[] infoPart = param.split("\\,", 2);
                String[] userDetail = infoPart[0].split(";" , 3);
                
                String userId = infoPart[1];
                List<String> updateList = List.of(userDetail[0], userDetail[1], userDetail[2]);
                boolean resultUserInfo = this.service.setUserInformation(updateList, userId);
                if (resultUserInfo) {
                    return "set_user_info:success";
                } else {
                    return "set_user_info:fail";
                }
            
            case "set_user_warning":
                System.out.println("set_user_warning을 동작하겠습니다.");
                boolean resultUserWarn = this.service.setUserWarning(param);
                
                if (resultUserWarn) {
                    return "set_user_warning:success";
                } else {
                    return "set_user_warning:fail";
                }
               
            case "get_check_user_id":
                System.out.println("get_check_user_id를 동작하겠습니다.");
                boolean resultUserCheckId = this.service.getIdCheck(param); // 아이디가 중복이면 true 중복이 아니면 false
                
                if (resultUserCheckId) {
                    return "get_check_user_id:success"; //아이디가 중복되었으므로 success를 반환
                } else {
                    return "get_check_user_id:fail";
                }
                
            case "add_user":
                System.out.println("add_user을 동작하겠습니다");
                List<String> newUser = List.of(param.split(",", -1));
                boolean resultAddUser = this.service.addUser(newUser);
                
                if (resultAddUser) {
                    return "add_user:success";
                } else {
                    return "add_user:fail";
                }
                
            default:
                System.out.println("알 수 없는 응답." + input);
                break;
        }
        return null;
    }
}
