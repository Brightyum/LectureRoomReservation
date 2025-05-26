/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import View.ClientFullView;
import Model.Inquiry;
import View.Professor.ProfessorFrame;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author leeseungmin
 */
public class professorClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isConnected = false;

    public professorClient() {
        connectToServer();
    }

    private void connectToServer() {
        try {
            this.socket = new Socket("localhost", 10020);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.isConnected = true;

            String serverMessage = in.readLine();
            if (serverMessage.contains("가득")) {
                new ClientFullView();
                close();
            } else if (!serverMessage.contains("접속하셨습니다.")) {
                JOptionPane.showMessageDialog(null, "서버 연결 실패: " + serverMessage);
                close();
            } else {
                out.println("ROLE=PROFESSOR");
                javax.swing.SwingUtilities.invokeLater(() -> {
                    new ProfessorFrame(this).setVisible(true);
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "서버 연결 실패: " + e.getMessage());
            close();
        }
    }

    public boolean sendInquiry(Inquiry inquiry) {
        if (!isConnected) {
            return false;
        }
        try {
            out.println("ADD_INQUIRY|" + inquiry.toNetworkString());
            return "SUCCESS".equals(in.readLine());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "문의 전송 실패: " + e.getMessage());
            return false;
        }
    }

    public boolean sendAnswerToServer(Inquiry inquiry, String answer) {
        if (!isConnected) {
            JOptionPane.showMessageDialog(null, "서버에 연결되지 않았습니다.");
            return false;
        }

        try {
            String request = String.format(
                    "UPDATE_ANSWER|%s|%s|%s|%s",
                    inquiry.getName(),
                    inquiry.getId(),
                    inquiry.getTime().toString(),
                    answer
            );
            out.println(request);

            String response = in.readLine();
            return "SUCCESS".equals(response);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "답변 전송 실패: " + e.getMessage());
            return false;
        }
    }

    public List<Inquiry> requestInquiryListFromServer() {
        List<Inquiry> inquiries = new ArrayList<>();
        if (!isConnected) {
            return inquiries;
        }
        try {
            out.println("GET_INQUIRY_LIST");
            String response;
            while (!(response = in.readLine()).equals("END")) {
                inquiries.add(Inquiry.fromNetworkString(response));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "문의 목록 불러오기 실패: " + e.getMessage());
        }
        return inquiries;
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
            isConnected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> requestRoomList() throws IOException {
        out.println("GET_ROOM_LIST");
        return readListResponse();
    }

    public List<Object[]> requestPastReservations(String roomID) throws IOException {
        out.println("GET_PAST_RESERVATIONS|" + roomID);
        List<Object[]> reservations = new ArrayList<>();
        String line;
        while (!(line = in.readLine()).equals("END")) {
            reservations.add(line.split("\\|"));
        }
        return reservations;
    }

    private List<String> readListResponse() throws IOException {
        List<String> list = new ArrayList<>();
        String response;
        while (!(response = in.readLine()).equals("END")) {
            list.add(response);
        }
        return list;
    }

    private List<Object[]> readTableData() throws IOException {
        List<Object[]> data = new ArrayList<>();
        String line;
        while (!(line = in.readLine()).equals("END")) {
            data.add(line.split("\\|"));
        }
        return data;
    }

    public static void main(String[] args) {
        new professorClient();
    }
}
