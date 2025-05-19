/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import admin.UserManagement;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author duatm
 */
public class AdminView extends JFrame {
    private JFrame frame;
    private JLabel label;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton checkButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton refreshButton;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private Client client;
    private UserManagement um;
    private AdminDetailUserView uv;
    private AdminAddUserView au;
    
    public AdminView(String message, Client client) {
        this.client = client;
        this.um = new UserManagement();
        
        List<String> userNames = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        
        userNames = this.um.getUsers();
        userIds = this.um.getId();
        
        int nameArrSize = userNames.size();
        int idArrSize = userIds.size();
        
        String[] usersArray = userNames.toArray(new String[nameArrSize]);
        String[] usersIdArray = userIds.toArray(new String[idArrSize]);
        
        this.userListModel = new DefaultListModel<>();
        
        //String[] userDisplayArray = new String[nameArrSize];
        for (int i = 0; i < nameArrSize; i++) {
            userListModel.addElement(usersArray[i] + "-" + usersIdArray[i]);
        }
        
        //기본 세팅
        this.frame = new JFrame("관리자");
        this.frame.setSize(300, 150);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        
        // 종료버튼 및 버튼 액션
        this.label = new JLabel(message, SwingConstants.CENTER);
        this.exitButton = new JButton("종료하기");
        
        this.exitButton.addActionListener(new ActionListener() {
            //@Overide
            public void actionPerformed(ActionEvent e) {
                AdminView.this.client.sendClose();
            }
        });
        
        //사용자 목록
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        System.out.println("사용자 목록: " + userList);
        
        //사용자 목록에서 사용자를 클릭후 세부사항 확인 버튼
        this.checkButton = new JButton("세부사항 확인");
        this.checkButton.addActionListener(new ActionListener() {
            //@Overide
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) userList.getSelectedValue();
                if (selectedUser != null) {
                    String[] parts = selectedUser.split("-");
                    if (parts != null) {
                        int idIdx = 1;
                        String id = parts[idIdx].trim();

                        // 새 창 열기
                        uv = new AdminDetailUserView(selectedUser, id, um, frame);
                    }
                }
            }
        });
        
        this.refreshButton = new JButton("목록 새로고침");
        this.refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> updateNames = um.getUsers();
                List<String> updateIds = um.getId();
                
                userListModel.clear();
                for (int i = 0; i < updateNames.size(); i++) {
                    userListModel.addElement(updateNames.get(i) + "-" + updateIds.get(i));
                }
            }
        });
        
        this.addButton = new JButton("사용자 추가하기");
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                au = new AdminAddUserView(um);
            }
        });
        
        this.removeButton = new JButton("사용자 삭제하기");
        this.removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) userList.getSelectedValue();
                if (selectedUser != null) {
                    String[] parts = selectedUser.split("-");
                    if (parts != null) {
                        int idIdx = 1;
                        String id = parts[idIdx].trim();
                        
                        if (um.removeUser(id)){
                            JOptionPane.showMessageDialog(null, "사용자가 성공적으로 삭제되었습니다.");
                        } else {
                            JOptionPane.showMessageDialog(null, "해당 아이디의 사용자를 찾을 수 없습니다.");
                        }
                    }
                }
            }
        });
        //버튼 영역
        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.buttonPanel.add(checkButton);
        this.buttonPanel.add(refreshButton);
        this.buttonPanel.add(exitButton);
        this.buttonPanel.add(addButton);
        this.buttonPanel.add(removeButton);

        //화면 추가
        this.frame.add(this.label, BorderLayout.NORTH);
        this.frame.add(scrollPane, BorderLayout.CENTER);
        this.frame.add(this.buttonPanel, BorderLayout.SOUTH);
        
        
        this.setLocationRelativeTo(null);
    }
    public void show() {
        this.frame.setVisible(true);
    }

}
