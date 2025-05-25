/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Professor;

import Client.professorClient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Model.Inquiry;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.table.*;

/**
 *
 * @author leeseungmin
 */
public class PshowInquiryFrame extends javax.swing.JFrame {

    private professorClient client;
    private List<Inquiry> sortedUnprocessed = new ArrayList<>();
    private List<Inquiry> sortedProcessed = new ArrayList<>();
    private List<Inquiry> processedInquiries = new ArrayList<>();
    private List<Inquiry> unprocessedInquiries = new ArrayList<>();

    public PshowInquiryFrame(professorClient client) {
        this.client = client;
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

    private void refreshLists() {
        System.out.println("서버에서 문의 목록 새로고침 시작");
        new SwingWorker< List< Inquiry>, Void>() {
            @Override
            protected List< Inquiry> doInBackground() throws Exception {
                List< Inquiry> inquiries = new ArrayList<>();
                try (Socket socket = new Socket("localhost", 10020); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    String welcomeMessage = in.readLine();
                    out.println("ROLE=PROFESSOR");
                    out.println("GET_INQUIRY_LIST");

                    String line;
                    while ((line = in.readLine()) != null && !line.equals("END")) {
                        Inquiry inquiry = Inquiry.fromNetworkString(line);
                        if (inquiry != null) {
                            inquiries.add(inquiry);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return inquiries;
            }

            @Override
            protected void done() {
                try {
                    List< Inquiry> inquiries = get();
                    processedInquiries.clear();
                    unprocessedInquiries.clear();
                    for (Inquiry inquiry : inquiries) {
                        if (inquiry.isChecked()) {
                            processedInquiries.add(inquiry);
                        } else {
                            unprocessedInquiries.add(inquiry);
                        }
                    }
                    updateListModels(); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        System.out.println("[DEBUG] processedInquiries:");
        for (Inquiry inquiry : processedInquiries) {
            System.out.println("- " + inquiry.getMessage() + " | 답변: " + inquiry.getAnsweredInquiries());
        }
        System.out.println("[DEBUG] unprocessedInquiries:");
        for (Inquiry inquiry : unprocessedInquiries) {
            System.out.println("- " + inquiry.getMessage() + " | 답변: " + inquiry.getAnsweredInquiries());
        }
    }

    private void updateListModels() {
        sortedUnprocessed = new ArrayList<>(unprocessedInquiries);
        sortedUnprocessed.sort((a, b) -> Boolean.compare(b.isPriority(), a.isPriority()));

        DefaultListModel< String> unprocessedModel = new DefaultListModel<>();
        for (Inquiry inquiry : sortedUnprocessed) {
            unprocessedModel.addElement(inquiry.getName() + "(" + inquiry.getId() + ")");
        }
        jList2.setModel(unprocessedModel);

        sortedProcessed = new ArrayList<>(processedInquiries);
        sortedProcessed.sort((a, b) -> Boolean.compare(b.isPriority(), a.isPriority()));

        DefaultListModel< String> processedModel = new DefaultListModel<>();
        for (Inquiry inquiry : sortedProcessed) {
            processedModel.addElement(inquiry.getName() + "(" + inquiry.getId() + ")");
        }
        jList1.setModel(processedModel);
    }

    private void showInquiryDetail(Inquiry inquiry, JTable table) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String message = (inquiry.getMessage() != null) ? inquiry.getMessage() : "";
        String time = (inquiry.getTime() != null) ? inquiry.getTime().format(formatter) : "N/A";
        String priority = inquiry.isPriority() ? "긴급" : "보통";
        String answer = (inquiry.getAnsweredInquiries() != null) ? inquiry.getAnsweredInquiries() : "";

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {
                        message,
                        time,
                        priority,
                        answer
                    }
                },
                new String[]{
                    "문의 내용",
                    "문의 날짜",
                    "중요도",
                    "답변"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
        System.out.println("[DEBUG] 선택된 문의 상세 정보:");
        System.out.println("문의 내용: " + inquiry.getMessage());
        System.out.println("답변 내용: " + inquiry.getAnsweredInquiries());
    }

    public static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jList2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = jList2.getSelectedIndex();
                if (index != -1) {
                    showInquiryDetail(sortedUnprocessed.get(index), jTable3);
                }
            }
        });

        jList1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = jList1.getSelectedIndex();
                if (index != -1) {
                    showInquiryDetail(sortedProcessed.get(index), jTable3);
                }
            }
        });

        refreshLists();
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new ProfessorFrame(client).setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged

    }//GEN-LAST:event_jList2ValueChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedIndex = jList2.getSelectedIndex(); 
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "문의를 선택하세요.");
            return;
        }

        Inquiry selectedInquiry = sortedUnprocessed.get(selectedIndex);
        String answer = jTextArea1.getText().trim();

        if (answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "답변을 입력하세요.");
            return;
        }

        final String request = String.format(
                "UPDATE_ANSWER|%s|%s|%s|%s",
                selectedInquiry.getName(),
                selectedInquiry.getId(),
                selectedInquiry.getTime().toString(),
                answer
        );

        new SwingWorker< Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try (
                        Socket socket = new Socket("localhost", 10020); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {                    
                    String welcome = in.readLine();
                    System.out.println("[클라이언트] 서버 환영: " + welcome);
 
                    out.println("ROLE=PROFESSOR");

                    out.println(request);
                    System.out.println("[클라이언트] 요청 전송: " + request);

                    String response = in.readLine();
                    System.out.println("[클라이언트] 서버 응답: " + response);

                    return "SUCCESS".equals(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        refreshLists();
                        jTextArea1.setText("");
                        JOptionPane.showMessageDialog(PshowInquiryFrame.this, "답변 등록 성공");
                    } else {
                        JOptionPane.showMessageDialog(PshowInquiryFrame.this, "답변 등록 실패");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }//GEN-LAST:event_jButton2ActionPerformed

    //===============================================================================================================================================================//
    public static void main(String[] args) {
        professorClient client = new professorClient();
        java.awt.EventQueue.invokeLater(() -> {
            new PshowInquiryFrame(client).setVisible(true);
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
