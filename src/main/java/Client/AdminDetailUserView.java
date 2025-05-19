/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import admin.UserManagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author duatm
 */
public class AdminDetailUserView extends JFrame{
     private JTable table;
    private UserManagement um;
    private String userId;
    private JFrame parentFrame;

    public AdminDetailUserView(String userDisplayText, String userId, UserManagement um, JFrame parentFrame) {
        this.um = um;
        this.userId = userId;
        this.parentFrame = parentFrame;

        setTitle(userDisplayText + "의 세부사항");
        setSize(400, 200);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        List<String> userDetail = um.getUserDetail(userId);
        String[] columns = {"이름", "아이디", "비밀번호", "학과", "경고"};
        String[][] tableData = new String[1][5];

        for (int i = 0; i < userDetail.size() - 2; i++) {
            tableData[0][i] = userDetail.get(i);
        }

        DefaultTableModel model = new DefaultTableModel(tableData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 1 && column != 4; // 아이디, 경고는 수정 불가
            }
        };

        table = new JTable(model);
        table.getColumnModel().getColumn(1).setCellEditor(null);
        table.getColumnModel().getColumn(4).setCellEditor(null);

        Dimension tableSize = new Dimension(
                table.getPreferredSize().width,
                table.getRowHeight() + table.getTableHeader().getPreferredSize().height
        );
        table.setPreferredScrollableViewportSize(tableSize);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton saveButton = new JButton("수정하기");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                String name = table.getValueAt(0, 0).toString();
                String pwd = table.getValueAt(0, 2).toString();
                String major = table.getValueAt(0, 3).toString();

                List<String> updateList = List.of(name, pwd, major);
                System.out.print("바뀐리스트");
                System.out.println(updateList);
                um.setUserInformation(updateList, userId);
                JOptionPane.showMessageDialog(AdminDetailUserView.this, "수정 내용이 저장되었습니다");
            }
        });
        
        JButton warningButton = new JButton("경고주기");
        warningButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String id = table.getValueAt(0, 1).toString();
               
               um.setUserWarning(id);
               
           }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(warningButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(contentPanel);
        pack();
    }
}
