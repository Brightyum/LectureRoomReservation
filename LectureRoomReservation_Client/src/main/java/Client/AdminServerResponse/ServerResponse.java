/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.AdminServerResponse;
import java.util.List;
import java.util.*;
import View.Admin.AdminView;
import View.Admin.AdminDetailUserView;
import View.Admin.AdminAddUserView;
import javax.swing.SwingUtilities;
/**
 *
 * @author user
 */

public class ServerResponse {
    private final int idx = 0;
    private final int data = 1;
    private AdminView view;
    private AdminDetailUserView detailView;
    private AdminAddUserView addView;
    private List<String> getDetail;
    
    public ServerResponse() {
        
    }
    public ServerResponse(AdminView view) {
        this.view = view;
    }
    
    public void judgeCommand(String input) {
        String[] parts = input.split(":", 2);
        
        String command = parts[idx].trim();
        String content = parts[data].trim();
        
        switch (command) {
            case "get_user_names":
                List<String> userList = Arrays.asList(content.split(","));
                view.setUserNames(userList);
                break;
                
            case "get_user_ids":
                List<String> userIds = Arrays.asList(content.split(","));
                view.setUserIds(userIds);
                break;
                
            case "remove_user":
                switch (content) {
                    case "success":
                            view.setResult(true);
                            break;
                        case "fail":
                            view.setResult(false);
                            break;
                }
                break;
            case "get_user_detail":
                System.out.println("get_user_detail을 실행하겠습니다.");
                List<String> userDetails = Arrays.asList(content.split(","));
                if (detailView != null) {
                    detailView.setUserDetail(userDetails);
                } else {
                    this.getDetail = userDetails;
                }
                
                break;
                
            case "set_user_info":
                switch (content) {
                    case "success":
                        detailView.setResult(true);
                        break;
                    case "fail":
                        detailView.setResult(false);
                        break;
                }
                break;
            case "set_user_warning":
                switch (content) {
                    case "success":
                        detailView.setWarningResult(true);
                        break;
                    case "fail":
                        detailView.setWarningResult(false);
                        break;
                }
                break;
            case "get_check_user_id":
                switch (content) {
                    case "success":
                        addView.setIdCheckResult(true);
                        break;
                    case "fail":
                        addView.setIdCheckResult(false);
                        break;   
                }
                break;
            case "add_user":
                switch(content) {
                    case "success":
                        SwingUtilities.invokeLater(() -> addView.setAddUserResult(true));
                        break;
                    case "fail":
                        SwingUtilities.invokeLater(() -> addView.setAddUserResult(false));
                        break;
                }
                break;
                
            default:
                //view.appendResponse("️알 수 없는 응답: " + command);
                break;
        }
    }
    
    public void setDetailView(AdminDetailUserView detailView) {
        this.detailView = detailView;
        
        if (getDetail != null) {
            System.out.println("[setDetailView] 저장된 데이터 반영 중: " + getDetail);
            detailView.setUserDetail(getDetail);
            getDetail = null;
        } else {
            System.out.println("[setDetailView] 대기 중인 데이터 없음");
        }
    }
    
    public void setAddView(AdminAddUserView addView) {
        this.addView = addView;
    }
    
    public void setAdminView(AdminView view) {
        this.view = view;
    }
}
