/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Controller.professor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Model.InquiryExcel;
import java.awt.Component;
import javax.swing.table.*;


/**
 *
 * @author leeseungmin
 */
public class PshowInquiryFrame extends javax.swing.JFrame {
    // 처리/미처리 문의 목록 저장 리스트
    private List<Inquiry> processedInquiries = new ArrayList<>();
    private List<Inquiry> unprocessedInquiries = new ArrayList<>();
    private InquiryExcel inquiryExcel = new InquiryExcel();
    
    public PshowInquiryFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("문의사항 보기");

        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("미처리 문의사항");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("처리된 문의사항");

        jButton1.setText("이전");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("답변 작성");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("답변 등록");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane5)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(187, 187, 187)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(92, 92, 92)
                                        .addComponent(jButton2)))))
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jButton2)
                        .addGap(33, 33, 33)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    jTable3.setDefaultRenderer(String.class, new MultiLineCellRenderer());
    // 데이터 초기 로드
    refreshLists();

    // jList1에 ListSelectionListener 추가 (처리된 문의사항)
    jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent e) { 
            if (!e.getValueIsAdjusting() && jList1.getSelectedIndex() != -1) {
                int index = jList1.getSelectedIndex();
                Inquiry selectedInquiry = processedInquiries.get(index);
                showInquiryDetail(selectedInquiry, jTable3);
            }
        }
    });

    // jList2에 ListSelectionListener 추가 (미처리 문의사항)
    jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent e) { 
            if (!e.getValueIsAdjusting() && jList2.getSelectedIndex() != -1) {
                int index = jList2.getSelectedIndex();
                Inquiry selectedInquiry = unprocessedInquiries.get(index);
                showInquiryDetail(selectedInquiry, jTable3);
            }
        }
    });

    // 답변 등록 버튼
    jButton2.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        Inquiry selected = null;
        if (jList1.getSelectedIndex() != -1) {
            selected = processedInquiries.get(jList1.getSelectedIndex());
        } else if (jList2.getSelectedIndex() != -1) {
            selected = unprocessedInquiries.get(jList2.getSelectedIndex());
        }
        if (selected != null) {
            String answer = jTextArea1.getText();
            inquiryExcel.updateAnswer(
                    selected.getName(),
                    selected.getId(),
                    selected.getTime().toString(),
                    answer
            );
            refreshLists();
            // 답변 등록 완료 메시지 표시
            JOptionPane.showMessageDialog(
                PshowInquiryFrame.this,
                "답변이 등록되었습니다.",
                "알림",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
});
    jTable3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent e) {
            int row = jTable3.rowAtPoint(e.getPoint());
            int col = jTable3.columnAtPoint(e.getPoint());
            if (row > -1 && col > -1) {
                Object value = jTable3.getValueAt(row, col);
                jTable3.setToolTipText(value != null ? value.toString() : null);
            }
        }
    });
    
    }//GEN-LAST:event_formWindowOpened

    // 데이터 로드 및 리스트 갱신
    private void refreshLists() {
        Map<String, List<Inquiry>> map = inquiryExcel.getProcessedAndUnprocessedInquiries();
        processedInquiries = map.get("processed");
        unprocessedInquiries = map.get("unprocessed");
        updateListModels();
        
        if (processedInquiries.isEmpty() && unprocessedInquiries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "등록된 문의사항이 없습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // JList 모델 업데이트
    private void updateListModels() {
    // 처리된 문의 정렬: 긴급(true) 먼저, 보통(false) 나중
    processedInquiries.sort((a, b) -> Boolean.compare(b.isPriority(), a.isPriority()));
    DefaultListModel<String> processedModel = new DefaultListModel<>();
    for (Inquiry inquiry : processedInquiries) {
        processedModel.addElement(inquiry.getName() + "(" + inquiry.getId() + ")");
    }
    jList1.setModel(processedModel);

    // 미처리 문의도 동일하게 정렬
    unprocessedInquiries.sort((a, b) -> Boolean.compare(b.isPriority(), a.isPriority()));
    DefaultListModel<String> unprocessedModel = new DefaultListModel<>();
    for (Inquiry inquiry : unprocessedInquiries) {
        unprocessedModel.addElement(inquiry.getName() + "(" + inquiry.getId() + ")");
    }
    jList2.setModel(unprocessedModel);
}
    private void showInquiryDetail(Inquiry inquiry, JTable table) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTime = inquiry.getTime() != null ? inquiry.getTime().format(formatter) : "";
        String priority = inquiry.isPriority() ? "긴급" : "보통";
        String answer = inquiry.getAnsweredInquiries() != null ? inquiry.getAnsweredInquiries() : "";
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
                { inquiry.getMessage(), formattedTime, priority, answer }
            },
            new String[] { "문의 내용", "문의 날짜", "중요도", "답변" }
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table.setModel(model);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new ProfessorFrame().setVisible(true);   
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged

    }//GEN-LAST:event_jList2ValueChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed
    

public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        int preferredHeight = getPreferredSize().height;
        if (table.getRowHeight(row) != preferredHeight) {
            table.setRowHeight(row, preferredHeight);
        }

        return this;
    }
}

    //===============================================================================================================================================================//
    public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> {
        new PshowInquiryFrame().setVisible(true);
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}

