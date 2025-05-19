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
public class AdminAddUserView extends JFrame {
    private JFrame frame;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel pwdLabel;
    private JLabel majorLabel;
    private JTextField nameField;
    private JTextField idField;
    private JTextField pwdField;
    private JTextField majorField;
    
    public AdminAddUserView(UserManagement um) {
        this.frame = new JFrame("사용자 추가 모드");
        this.frame.setSize(300, 150);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        
        this.nameLabel = new JLabel("이름: ");
        this.idLabel = new JLabel("아이디: ");
        this.pwdLabel = new JLabel("비밀번호: ");
        this.majorLabel = new JLabel("학과: ");
        
        this.nameField = new JTextField();
        this.idField = new JTextField();
        this.pwdField = new JTextField();
        this.majorField = new JTextField();
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JButton idCheckButton = new JButton("아이디 중복확인");
        idCheckButton.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               String id = idField.getText().trim();
               if (id.isEmpty()) {
                   JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                   return;
               }

               if (um.getIdCheck(id)) {    
                   JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.", "중복", JOptionPane.WARNING_MESSAGE);
               } else {
                   JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
               }
           }
        });
        
        JButton addButton = new JButton("추가하기");
        addButton.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               String name = nameField.getText().trim();
               String id = idField.getText().trim();
               String pwd = pwdField.getText().trim();
               String major = majorField.getText().trim();
               
               if (name.isEmpty() || id.isEmpty() || pwd.isEmpty() || major.isEmpty()) {
                   JOptionPane.showMessageDialog(null, "모든 칸을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                   return;
               }
               List<String> user = new ArrayList<>();
               user.add(name);
               user.add(id);
               user.add(pwd);
               user.add(major);
               user.add("0");
               user.add("-");
               
               if (um.addUser(user)) {
                   System.out.println("사용자 추가 완료");
               } else {
                   System.out.println("사용자 추가 실패");
               }
           }
        });
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(pwdLabel);
        formPanel.add(pwdField);
        formPanel.add(majorLabel);
        formPanel.add(majorField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(idCheckButton);
        buttonPanel.add(addButton);
        
        this.frame.add(formPanel, BorderLayout.CENTER);
        this.frame.add(buttonPanel, BorderLayout.SOUTH);
        this.frame.setVisible(true);
    }
}
