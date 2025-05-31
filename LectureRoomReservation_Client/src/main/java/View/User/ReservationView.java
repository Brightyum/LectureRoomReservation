/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.User;

import Client.UserClient;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.io.PrintWriter;
import Client.MessageSender;
/**
 *
 * @author user
 */
public class ReservationView extends JFrame {
    
    private MessageSender sender;

    private JComboBox<String> roomCombo;
    private JTextField seatField;
    private JTextField nameField;
    private JTextField idField;
    private JComboBox<String> timeCombo;
    private DatePicker datePicker;

    public ReservationView(MessageSender sender) {
        this.sender = sender;

        setTitle("강의실 예약 시스템");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 상단 제목
        JLabel titleLabel = new JLabel("강의실 예약 시스템", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 입력 패널 (GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // 강의실
        formPanel.add(new JLabel("강의실:"), gbc);
        gbc.gridx = 1;
        String[] rooms = {"911", "912", "913", "914", "915", "916", "917"};
        roomCombo = new JComboBox<>(rooms);
        formPanel.add(roomCombo, gbc);

        // 좌석
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("좌석 번호 (1~40):"), gbc);
        gbc.gridx = 1;
        seatField = new JTextField();
        formPanel.add(seatField, gbc);

        // 이름
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("이름:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        formPanel.add(nameField, gbc);

        // ID
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField();
        formPanel.add(idField, gbc);

        // 날짜
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("날짜 선택:"), gbc);
        gbc.gridx = 1;
        DatePickerSettings settings = new DatePickerSettings();
        settings.setAllowKeyboardEditing(false);
        datePicker = new DatePicker(settings);
        formPanel.add(datePicker, gbc);

        // 시간
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("시간 선택:"), gbc);
        gbc.gridx = 1;
        String[] times = {"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
        timeCombo = new JComboBox<>(times);
        formPanel.add(timeCombo, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton reserveButton = new JButton("예약하기");
        JButton cancelButton = new JButton("닫기");

        reserveButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(reserveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // 버튼 동작
        reserveButton.addActionListener(e -> {
            String room = (String) roomCombo.getSelectedItem();
            String seat = seatField.getText().trim();
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            String date = (datePicker.getDate() != null) ? datePicker.getDate().toString() : "";
            String time = (String) timeCombo.getSelectedItem();

            if (seat.isEmpty() || name.isEmpty() || id.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요.");
                return;
            }
            String message = String.format("TRY_RESERVATION|%s|%s|%s|%s|%s|%s", room, seat, name, id, date, time);
            sender.sendMessage(message);
           // JOptionPane.showMessageDialog(this, "예약 요청을 보냈습니다.");
        });

        cancelButton.addActionListener(e -> dispose());

    }
    
}
