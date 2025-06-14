/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Professor;

import Client.professorClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 과거 강의실 사용 내역을 조회하는 프레임입니다.
 * 강의실 목록 , 예약 내역 조회의 기능을 제공합니다.
 * 
 * @author leeseungmin
 */
public class PastRosterFrame extends javax.swing.JFrame {
    private professorClient client;         // 서버와 통신하는 교수 클라이언트 객체
    private DefaultTableModel tableModel;   // 강의실 예약 내역을 표시하는 테이블의 데이터 모델
    
    /**
     * 창이 열릴때 강의실 목록을 불러옵니다.
     * 사용자가 강의실을 선택할경우 해당 강의실의 과거 내역이 출력됩니다.
     * 
     * @param client 
     */
    public PastRosterFrame(professorClient client) {
        this.client = client;
        initComponents();
        initializeTable();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                loadRoomList();
            }
        });

        jList1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedRoom = jList1.getSelectedValue();
                if (selectedRoom != null) {
                    loadReservations(selectedRoom);
                }
            }
        });


        jButton1.addActionListener(e -> dispose());
    }
    
    /**
     * Jtable을 초기화하는 기능입니다.
     */
    private void initializeTable() {
        tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0); 
    }
    
    /**
     * 서버에서 .xlsx 파일의 시트를 읽어 강의실 리스트를 Jlist에 출력하는 기능입니다.
     * 서버에서 예약내역 불러오지 못한다면 "강의실 목록 불러오기 실패" 알림창을 띄웁니다.
     */
    private void loadRoomList() {
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                List<String> rooms = client.requestRoomList(); 
                System.out.println("[클라이언트] 받은 강의실 목록: " + rooms);
                return rooms;
            }

            @Override
            protected void done() {
                try {
                    List<String> rooms = get();
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for (String room : rooms) {
                        model.addElement(room);
                    }
                    jList1.setModel(model);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(PastRosterFrame.this, "강의실 목록 불러오기 실패");
                }
            }
        }.execute();
    }
    
    /**
     * Jlist에서 강의실을 선택한다면 Jtable에 강의실호실의 과거 예약 내역을 불러오는 기능입니다.
     * 서버에서 예약내역을 불러오지 못한다면 "예약 내역 불러오기 실패" 알람을 띄웁니다.
     * 
     * @param roomID 강의실 호실
     */
    private void loadReservations(String roomID) {
        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                return client.requestPastReservations(roomID); 
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> reservations = get();
                    tableModel.setRowCount(0); 
                    LocalDateTime now = LocalDateTime.now();

                    for (Object[] row : reservations) {
                        String dateStr = row[3].toString();
                        String timeStr = row[4].toString();

                        LocalDate date;
                        if (dateStr.contains("-")) {
                            date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        } else if (dateStr.contains(".")) {
                            date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                        } else {
                            continue;
                        }
                        LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
                        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);

                        if (reservationDateTime.isBefore(now)) {
                            tableModel.addRow(row);
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(PastRosterFrame.this, "예약 내역 불러오기 실패");
                }
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("과거 강의실 사용내역");

        jButton1.setText("이전");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("강의실 선택");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "좌석 번호", "이름", "아이디", "날짜", "시간"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new ProfessorFrame(client).setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
