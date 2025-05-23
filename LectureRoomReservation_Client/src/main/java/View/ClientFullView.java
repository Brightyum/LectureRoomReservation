/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Client.Client;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author user
 */
public class ClientFullView extends JFrame {
    private JFrame frame;
    private JLabel label;
    private JButton exitButton;
    private Client client;
    
    public ClientFullView() {
//        this.client = client;
        this.frame = new JFrame("관리자");
        this.frame.setSize(500, 150);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        
        this.label = new JLabel("서버에 이용자가 가득 찼습니다. 나중에 다시 시도하세요.");
        
//        this.exitButton.addActionListener(new ActionListener() {
//            //@Overide
//            public void actionPerformed(ActionEvent e) {
//                ClientFullView.this.client.sendClose();
//            }
//        });
        
        this.frame.add(this.label, BorderLayout.CENTER);
        //this.frame.add(this.exitButton, BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }
}
