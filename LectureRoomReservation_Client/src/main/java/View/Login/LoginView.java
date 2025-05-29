/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Client.Login;
import Client.Client;
import Client.UserClient;
import Client.professorClient;
import View.Admin.AdminView;
import View.Login.SignUpView;

/**
 *
 * @author user
 */
public class LoginView extends JFrame {
    private Login client;
    private boolean loginResult;
    private String id;
    private String pwd;
    
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    
    public LoginView(Login login) {
        this.client = login;

        setTitle("로그인");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 배치

        // 레이아웃 설정
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 간 여백

        // 아이디 라벨과 텍스트 필드
        JLabel idLabel = new JLabel("아이디:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(idLabel, gbc);

        idField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(idField, gbc);

        // 비밀번호 라벨과 비밀번호 필드
        JLabel passwordLabel = new JLabel("비밀번호:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // 로그인 버튼
        loginButton = new JButton("로그인");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // 아래쪽 버튼 패널 (아이디찾기, 비밀번호찾기, 회원가입)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        signUpButton = new JButton("회원가입");
        buttonPanel.add(signUpButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // 버튼 이벤트 리스너 등록 (예시 - 실제 동작 구현 필요)
        loginButton.addActionListener(e -> {
            id = idField.getText();
            pwd = new String(passwordField.getPassword());
            
            if (id != null && pwd != null) {
                client.sendMessage("check_login:" + id + "," + pwd);
            }
            System.out.println("로그인 시도: " + id + ", " + pwd);
        });

        signUpButton.addActionListener(e -> {
            // 회원가입 화면 전환 또는 회원가입 로직 구현 필요
            JOptionPane.showMessageDialog(this, "회원가입 기능은 구현 중입니다.");
            this.setSignUpView(client);
        });

        setVisible(true);
    }
    
    public void checkLogin(boolean result) {
        if (result) {
            this.loginResult = true;
            JOptionPane.showMessageDialog(this, "로그인 성공");
            client.sendMessage("check_login_role:" + id + "," + pwd);
        } else {
            this.loginResult = false;
            JOptionPane.showMessageDialog(this, "로그인 실패");
        }
    }
    
    public void checkUserRole(String result) {
        switch (result) {
            case "학생":
                JOptionPane.showMessageDialog(this, "학생");
                javax.swing.SwingUtilities.invokeLater(() -> {
                    new UserClient(); // UserClient 인스턴스를 생성하여 사용자 화면 실행
                });
                this.dispose();
                break;
            case "교수":
                JOptionPane.showMessageDialog(this, "교수");
                professorClient professorview = new professorClient();
                this.dispose();
                break;
            case "관리자":
                JOptionPane.showMessageDialog(this, "관리자");
                Client client = new Client();
                AdminView adminView = new AdminView(client);
                client.getServerResponse().setAdminView(adminView);
                this.dispose();
                break;
        }
    }
    
    public void setSignUpView(Login login) {
        SignUpView view = new SignUpView(login);
        login.getServerResponse().setSignUpView(view);
    }
        
}
