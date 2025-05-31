/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Login;

import javax.swing.*;
import java.awt.*;
import Client.Login;

/**
 *
 * @author user
 */
public class SignUpView extends JFrame {
    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField departmentField;
    private JComboBox<String> roleComboBox;
    private JButton signUpButton;
    private Login client;
    public SignUpView(Login login) {
        this.client = login;
        setTitle("회원가입");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창만 닫기
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // 이름
        JLabel nameLabel = new JLabel("이름:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // 아이디
        JLabel idLabel = new JLabel("아이디:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(idLabel, gbc);

        idField = new JTextField(15);
        gbc.gridx = 1;
        add(idField, gbc);

        // 비밀번호
        JLabel passwordLabel = new JLabel("비밀번호:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // 학과
        JLabel deptLabel = new JLabel("학과:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(deptLabel, gbc);

        departmentField = new JTextField(15);
        gbc.gridx = 1;
        add(departmentField, gbc);

        // 사용자 선택
        JLabel roleLabel = new JLabel("사용자 선택:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[] {"학생", "교수", "관리자"});
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        // 회원가입 버튼
        signUpButton = new JButton("회원가입하기");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(signUpButton, gbc);

        // 버튼 동작 (예시)
        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            String dept = departmentField.getText();
            String role = (String) roleComboBox.getSelectedItem();
            
            client.sendMessage("signup:"+name+ "," + id+ "," + password+ "," + dept+ "," + role);
            // 회원가입 처리 로직 (예: DB 저장 등) 추가 필요
            //JOptionPane.showMessageDialog(this, "회원가입 성공!\n이름: " + name + "\n아이디: " + id + "\n역할: " + role);
            dispose(); // 회원가입 완료 후 창 닫기
        });

        setVisible(true);
    }
    
    public void checkSignUp(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(this, "회원가입에 성공하셨습니다");
        } else {
            JOptionPane.showMessageDialog(this, "회원가입에 실패하셨습니다, 아이디가 같거나 동작 실패입니다.");
        }
    }
}
