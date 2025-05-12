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
    private JButton exitButton;
    private JList userList;
    private Client client;
    private UserManagement um;
    
    public AdminView(String message, Client client) {
        this.client = client;
        this.um = new UserManagement();
        
        List<String> userNames = new ArrayList<>();
        userNames = this.um.getUsers();
        
        this.frame = new JFrame("관리자");
        this.frame.setSize(300, 150);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        
        this.label = new JLabel(message, SwingConstants.CENTER);
        this.exitButton = new JButton("종료하기");
        
        this.exitButton.addActionListener(new ActionListener() {
            //@Overide
            public void actionPerformed(ActionEvent e) {
                AdminView.this.client.sendClose();
            }
        });
        
        int arrSize = userNames.size();
        String[] usersArray = userNames.toArray(new String[arrSize]);
        
        userList = new JList<>(usersArray);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        
        this.frame.add(label, BorderLayout.CENTER);
        this.frame.add(exitButton, BorderLayout.SOUTH);
        this.frame.add(scrollPane, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
    }
    public void show() {
        this.frame.setVisible(true);
    }
}
