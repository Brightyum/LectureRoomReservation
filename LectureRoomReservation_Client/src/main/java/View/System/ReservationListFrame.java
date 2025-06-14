/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.System;

import java.awt.event.ActionEvent;
import Client.ReservationListClient;
import java.io.IOException;

/**
 *
 * @author user
 */
public class ReservationListFrame extends javax.swing.JFrame {

    public ReservationListFrame(String inputDate, String selectedRoom) throws IOException {
        initComponents();

        date.setText(inputDate);      // 날짜 필드 표시
        roomNum.setText(selectedRoom); // 강의실 필드 표시

        try {
            ReservationListClient client = new ReservationListClient();
            String response = client.requestReservationList(selectedRoom, inputDate);
            client.close();

            StringBuilder sb = new StringBuilder();

            if (response.startsWith("SUCCESS|")) {
                String data = response.substring("SUCCESS|".length());
                String[] rows = data.split(";");
                for (String row : rows) {
                    sb.append(row).append("\n");
                }
            } else if (response.startsWith("EMPTY|")) {
                sb.append("해당 날짜에 예약된 정보가 없습니다.");
            } else {
                sb.append("서버 오류: ").append(response);
            }

            reservationList.setText(sb.toString());  // 예약 목록 출력
        } catch (IOException e) {
            reservationList.setText("서버 통신 오류: " + e.getMessage());
        }

        select.addActionListener((ActionEvent e) -> {
            dispose();
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        roomNum = new javax.swing.JTextField();
        select = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reservationList = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("호 예약 목록");

        date.setEditable(false);
        date.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        date.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        roomNum.setEditable(false);
        roomNum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        roomNum.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        select.setText("확인");
        select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });

        reservationList.setEditable(false);
        reservationList.setColumns(20);
        reservationList.setRows(5);
        jScrollPane1.setViewportView(reservationList);

        jLabel2.setText("/");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(select))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roomNum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 98, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(roomNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(select)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectActionPerformed
        // TODO 확인 버튼
    }//GEN-LAST:event_selectActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea reservationList;
    private javax.swing.JTextField roomNum;
    private javax.swing.JButton select;
    // End of variables declaration//GEN-END:variables
}
