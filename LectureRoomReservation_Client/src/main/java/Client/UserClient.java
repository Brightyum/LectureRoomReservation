/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;


import Model.Inquiry;
import View.ClientFullView;
import View.User.UserFrame;
import View.User.ReservationView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * 사용자 클라이언트의 서버 통신 및 문의 관리 기능을 담당하는 클래스입니다.
 * 문의 작성 및 본인 문의 조회 기능을 제공합니다.
 *
 * @author leeseungmin
 */
public class UserClient implements MessageSender {

    private Socket socket;                  // 서버와의 네트워크를 연결을 담당하는 소캣 객체
    private PrintWriter out;                // 서버로 메세지를 전송하기 위한 출력 스트림
    private BufferedReader in;              // 서버로 메세지를 수신하기 위한 입력 스트림
    private boolean isConnected = false;    // 서버와의 연결 상태를 나타내는 플래그
    private ReservationView reservationView;
    
    public UserClient() {
        connectToServer();
    }

    /**
     * 서버가 정상적으로 접속되면 교수역할을 서버에 알리고 교수 메인화면을 실행합니다.
     * 서버 연결에 실패하면 "서버 연결 실패" 알림창을 띄웁니다.
     */
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
                out.println("ROLE=USER");
                listenFromServer();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    new UserFrame(this).setVisible(true);
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "서버 연결 실패: " + e.getMessage());
            close();
        }
    }

    /**
     * inquiry 객체를 서버에 전송하는 기능입니다. inquiry 전송에 실패하면 "문의 전송 실패" 알람을 띄웁니다.
     *
     * @param inquiry 전송할 inquiry 객체
     * @return 전송 성공시 true , 실패시 false
     */
    public boolean sendInquiry(Inquiry inquiry) {
        if (!isConnected) {
            return false;
        }
        try {
            String request = String.format("CREATE_INQUIRY|%s|%s|%s|%s|%s",
                    inquiry.getName(), inquiry.getId(), inquiry.getMessage(),
                    inquiry.getTime().toString(), inquiry.isPriority());
            out.println(request);
            return "SUCCESS".equals(in.readLine());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "문의 전송 실패: " + e.getMessage());
            return false;
        }
    }

    /**
     * 서버에서 문의내역을 요청하여 inquiry 리스트로 반환하는 기능입니다. 서버에서 문의 목록을 불러오지못할때 "문의 목록 불러오기
     * 실패"라는 알람을 띄웁니다.
     *  
     * @param userId 조회에 사용할 아이디
     * @return 서버로부터 받은 객체 inquiry 객체의 리스트 , 연결 실패시 빈 리스트를 반환합니다.
     */
    public List<Inquiry> requestInquiryListFromServer(String userId) {
        List<Inquiry> inquiries = new ArrayList<>();
        try {
            out.println("GET_MY_INQUIRIES|" + userId);
            String response;
            while (!(response = in.readLine()).equals("END")) {
                Inquiry inquiry = Inquiry.fromNetworkString(response);
                if (inquiry != null) {
                    inquiries.add(inquiry);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "문의 목록 불러오기 실패: " + e.getMessage());
        }
        return inquiries;
    }
    
    @Override
    public void sendMessage(String msg) {
        out.println(msg);
    }
    
    public void listenFromServer() {
        new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println("[서버 응답] " + response);
                    handleServerMessage(response);
                }
            } catch (IOException e) {
                System.out.println("서버 응답 수신 중 오류: " + e.getMessage());
            }
        }).start();
    }

    // 서버에서 받은 메시지 처리 로직
    private void handleServerMessage(String response) {
        String[] parts = response.split("\\|");
        String command = parts[0];

        switch (command) {
            case "TRY_RESERVATION_SUCCESS":
                JOptionPane.showMessageDialog(null, "예약이 성공적으로 처리되었습니다.");
                break;
            case "TRY_RESERVATION_FAIL":
                JOptionPane.showMessageDialog(null, "예약 실패: " + (parts.length > 1 ? parts[1] : ""));
                break;
            default:
                System.out.println("알 수 없는 명령: " + command);
                break;
        }
    }
    
    public PrintWriter getOut() {
        return this.out;
    }
    
    /**
     * 서버와의 네트워크연결 및 입출력 스트림을 종료하는 기능입니다. 예외 발생시 콘솔에 에러 로그를 출력합니다.
     */
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

    public static void main(String[] args) {
        new UserClient();
    }
}
