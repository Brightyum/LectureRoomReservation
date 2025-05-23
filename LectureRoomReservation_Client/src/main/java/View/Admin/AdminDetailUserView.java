/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin;

import Client.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class AdminDetailUserView extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private List<String> userDetail;
    private Client client;
    private int idIdx;
    private int warnIdx;
    private int defaultIdx;
    
    public AdminDetailUserView(String userDisplayText, String userId, JFrame parentFrame, Client client) {
        this.client = client;
        this.idIdx = 1;
        this.warnIdx = 4;
        this.defaultIdx = 0;
        
        
        setTitle(userDisplayText + "의 세부사항");
        setSize(400, 200);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.userDetail = new ArrayList<>();
        //this.client.sendMessage("get_user_detail:" + userId);
        
        String[] columns = {"이름", "아이디", "비밀번호", "학과", "경고"};
        String[][] tableData = new String[1][5];
        
        for (int i = 0; i < userDetail.size() - 2; i++) {
            tableData[0][i] = userDetail.get(i);
        }

        model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != idIdx && column != warnIdx; // 아이디, 경고는 수정 불가
            }
        };
        
        table = new JTable(model);
        table.getColumnModel().getColumn(idIdx).setCellEditor(null);
        table.getColumnModel().getColumn(warnIdx).setCellEditor(null);

        Dimension tableSize = new Dimension(
                table.getPreferredSize().width,
                table.getRowHeight() + table.getTableHeader().getPreferredSize().height
        );
        table.setPreferredScrollableViewportSize(tableSize);

        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton saveButton = new JButton("수정하기");
        saveButton.addActionListener(e -> {
          if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                int nameIdx = 0;
                int pwdIdx = 2;
                int majorIdx = 3;
                
                String name = table.getValueAt(defaultIdx, nameIdx).toString();
                String pwd = table.getValueAt(defaultIdx, pwdIdx).toString();
                String major = table.getValueAt(defaultIdx, majorIdx).toString();

                List<String> updateList = List.of(name, pwd, major);
                System.out.print("바뀐리스트");
                System.out.println(updateList);
                
                String userData = String.join(";", updateList);
                
                this.client.sendMessage("set_user_info:" + userData + "," + userId);
        });
        
        JButton warningButton = new JButton("경고주기");
        warningButton.addActionListener(e -> {
          String id = table.getValueAt(defaultIdx, idIdx).toString();
          this.client.sendMessage("set_user_warning:" + id);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(warningButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(contentPanel);
        
        setVisible(true);
    }
    
    public void setUserDetail(List<String> userDetail) {
        System.out.println("setUserDetail 호출됨: " + userDetail);
        this.userDetail = userDetail;
        if (userDetail.size() >= 5) {
            if (model.getRowCount() == 0) {
                model.setRowCount(1); 
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("→ [" + i + "] " + userDetail.get(i));
                model.setValueAt(userDetail.get(i), 0, i);
            }
        } else {
            System.err.println("userDetail 사이즈 부족: " + userDetail.size());
        }
    }
    
    public void setResult(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(AdminDetailUserView.this, "수정 내용이 저장되었습니다");
        } else {
            JOptionPane.showMessageDialog(AdminDetailUserView.this, "수정 내용이 저장되지 않았습니다");
        }
    }
    
    public void setWarningResult(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(AdminDetailUserView.this, "경고 내용이 저장되었습니다");
        } else {
            JOptionPane.showMessageDialog(AdminDetailUserView.this, "경고 내용이 저장되지 않았습니다");
        }
    }
}
