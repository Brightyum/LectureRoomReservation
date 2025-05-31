/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import Client.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author user
 */
public class AdminView extends JFrame {
    private JFrame frame;
    private JTextArea displayArea;
    private List<String> userNames;
    private List<String> userIds;
    private Client client;
    private boolean removeResult;
    private AdminDetailUserView uv;
    private AdminAddUserView auv;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public AdminView(Client client) {
        this.client = client;
        this.userNames = new ArrayList<>();
        this.userIds = new ArrayList<>();
        
        // 기본 프레임 세팅
        this.frame = new JFrame("관리자");
        this.frame.setSize(500, 500);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        // 사용자 리스트 뷰
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("사용자 목록"));
        
        // 버튼 구성
        JButton exitButton = new JButton("종료하기");
        exitButton.addActionListener(e -> client.sendClose());

        JButton checkButton = new JButton("세부사항 확인");
        checkButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                String[] parts = selectedUser.split("-");
                if (parts.length >= 2) {
                    String id = parts[1].trim();
                    
                    //uv = new AdminDetailUserView(selectedUser, id, frame, this.client);
                    setDetail(selectedUser, id, this.client);
                    System.out.println("새창을 열것입니다. 아이디: " + id);
                    // 여기에 새 창 열기 로직
                }
            }
        });

        JButton refreshButton = new JButton("목록 새로고침");
        refreshButton.addActionListener(e -> refreshUserList());

        JButton addButton = new JButton("사용자 추가하기");
        addButton.addActionListener(e -> {
            setAdd(client);
            //auv = new AdminAddUserView(client);
        });

        JButton removeButton = new JButton("사용자 삭제하기");
        removeButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                String[] parts = selectedUser.split("-");
                if (parts.length >= 2) {
                    String id = parts[1].trim();
                    client.sendMessage("remove_user:" + id);
                    
                    JOptionPane.showMessageDialog(this, "사용자가 성공적으로 삭제되었습니다.");
                }
            }
        });

        // 버튼 패널 구성
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(checkButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(exitButton);

        this.frame.add(scrollPane, BorderLayout.CENTER);
        this.frame.add(buttonPanel, BorderLayout.SOUTH);
        this.frame.setVisible(true);

        // 사용자 목록 요청
        refreshUserList();
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = new ArrayList<>(userNames);
        System.out.println("사용자 목록");
        System.out.println(this.userNames);
        updateUserListIfReady();
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = new ArrayList<>(userIds);
        System.out.println("사용자 아이디 목록");
        System.out.println(this.userIds);
        updateUserListIfReady();
    }

    public void setResult(boolean result) {
        this.removeResult = result;
    }

    public void refreshUserList() {
        this.userIds.clear();
        this.userNames.clear();
        client.sendMessage("get_user_names");
        client.sendMessage("get_user_ids");
    }

    private void updateUserListIfReady() {
        if (userNames != null && userIds != null && userNames.size() == userIds.size()) {
            if (userNames.size() <= 1 || userIds.size() <= 1) {
                userListModel.clear();
                userListModel.addElement("등록된 사용자 없음");
                return;
            }

            userListModel.clear();
            for (int i = 1; i < userNames.size(); i++) {
                userListModel.addElement(userNames.get(i) + " - " + userIds.get(i));
            }
        }
    }

    public void appendResponse(String result) {
        JOptionPane.showMessageDialog(this, "요청을 처리 못했습니다.\n" + result, "요청 실패", JOptionPane.ERROR_MESSAGE);
    }
    
    public void setDetail(String text, String userId, Client client) {
        AdminDetailUserView detailView = new AdminDetailUserView(text, userId, this, client);
        client.getServerResponse().setDetailView(detailView);
        System.out.println("[AdminView] detailView 등록 완료");
        client.sendMessage("get_user_detail:" + userId);
    }
    
    public void setAdd(Client client) {
        AdminAddUserView addView = new AdminAddUserView(client);
        client.getServerResponse().setAddView(addView);
    }
}

