/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;
import javax.swing.*;
import java.awt.*;
import Client.Client;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class AdminAddUserView extends JFrame {
    private boolean idResult;
    private boolean addUserResult;
    private JTextField nameField;
    private JTextField idField;
    private JTextField pwdField;
    private JTextField majorField;

    public AdminAddUserView(Client client) {
        setTitle("사용자 추가 모드");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JLabel nameLabel = new JLabel("이름: ");
        JLabel idLabel = new JLabel("아이디: ");
        JLabel pwdLabel = new JLabel("비밀번호: ");
        JLabel majorLabel = new JLabel("학과: ");
        
        nameField = new JTextField();
        idField = new JTextField();
        pwdField = new JTextField();
        majorField = new JTextField();
        
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JButton idCheckButton = new JButton("아이디 중복 확인");
        idCheckButton.addActionListener( e -> {
          String id = idField.getText().trim();
          if (id.isEmpty()) {
              JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
              return;
          }
          client.sendMessage("get_check_user_id:" + id);
          if (this.idResult) {
              //아이디 중복
              JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.", "중복", JOptionPane.WARNING_MESSAGE);
          } else {
              //아이디 중복 아님
              JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
          }
        });
        
        JButton addButton = new JButton("추가하기");
        addButton.addActionListener( e -> {
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
            
            String userData = String.join(",", user);
            client.sendMessage("add_user:" + userData);
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
        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
    
    public void setIdCheckResult(boolean result) {
        this.idResult = result;
    }
    
    public void setAddUserResult(boolean result) {
        SwingUtilities.invokeLater(() -> {
            if (result) {
                JOptionPane.showMessageDialog(this, "사용자 추가 완료되었습니다.");
                this.dispose(); // 성공 시 창 닫기
            } else {
                JOptionPane.showMessageDialog(this, "사용자 추가 실패하였습니다.", "추가 실패", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    
}
