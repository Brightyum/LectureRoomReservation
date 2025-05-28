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
 * 교수 클라이언트의 서버통신 및 문의,강의실 관리 기능을 담당하는 클래스 입니다. 
 * 전체문의조회 , 강의실 목록 및 예약내역 조회 기능을 제공합니다.
 *
 * @author leeseungmin
 */
public class professorClient {

    private Socket socket;                  // 서버와의 네트워크를 연결을 담당하는 소캣 객체
    private PrintWriter out;                // 서버로 메세지를 전송하기 위한 출력 스트림
    private BufferedReader in;              // 서버로 메세지를 수신하기 위한 입력 스트림
    private boolean isConnected = false;    // 서버와의 연결 상태를 나타내는 플래그

    public professorClient() {
        connectToServer();
    }

    /**
     * 서버가 정상적으로 접속되면 교수역할을 서버에 알리고 교수 메인화면을 실행합니다. 서버연결이 되지않으면 "서버 연결 실패"라는
     * 알림창을 띄웁니다.
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
            out.println("ADD_INQUIRY|" + inquiry.toNetworkString());
            return "SUCCESS".equals(in.readLine());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "문의 전송 실패: " + e.getMessage());
            return false;
        }
    }

    /**
     * 문의내용에 대한 답변을 서버에 전송하는 기능입니다. 답변 전송이 되지않을경우 "답변 전송 실패" 알람을 띄웁니다.
     *
     * @param inquiry 답변할 inquiry 객체
     * @param answer 답변 내용
     * @return 전송 성공시 true , 실패시 false
     */
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

    /**
     * 서버에서 문의내역을 요청하여 inquiry 리스트로 반환하는 기능입니다. 서버에서 문의 목록을 불러오지못할때 "문의 목록 불러오기
     * 실패"라는 알람을 띄웁니다.
     *
     * @return 서버로부터 받은 객체 inquiry 객체의 리스트 , 연결 실패시 빈 리스트를 반환합니다.
     */
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

    /**
     * 서버에서 강의실호실 목록을받아오고 그 결과를 문자열 리스트로 반환합니다.
     *
     * @return 서버로 부터 받은 강의실 호실 list
     * @throws IOException 서버와 통신중 입출력 오류가 발생할 경우
     */
    public List<String> requestRoomList() throws IOException {
        out.println("GET_ROOM_LIST");
        return readListResponse();
    }

    /**
     * 선택한 강의실호실의 과거 사용내역을 불러오는 기능입니다.
     *
     * @param roomID 강의실호실
     * @return 과거 내역이 담겨져있는 Object배열 리스트
     * @throws IOException 서버와 통신중 입출력 오류가 발생할 경우
     */
    public List<Object[]> requestPastReservations(String roomID) throws IOException {
        out.println("GET_PAST_RESERVATIONS|" + roomID);
        List<Object[]> reservations = new ArrayList<>();
        String line;
        while (!(line = in.readLine()).equals("END")) {
            reservations.add(line.split("\\|"));
        }
        return reservations;
    }

    /**
     * 서버로부터 여러줄의 문자열 데이터를받아 리스트로변환하는 기능입니다.
     *
     * @return 서버에서 받은 배열 리스트
     * @throws IOException 서버와 통신중 입출력 오류가 발생할 경우
     */
    private List<String> readListResponse() throws IOException {
        List<String> list = new ArrayList<>();
        String response;
        while (!(response = in.readLine()).equals("END")) {
            list.add(response);
        }
        return list;
    }

    /**
     * 서버로부터 테이블 데이터의 데이터를받아 리스트로 변환하는 기능입니다.
     *
     * @return 테이블의 데이터 Object리스트
     * @throws IOException 서버와 통신중 입출력 오류가 발생할 경우
     */
    private List<Object[]> readTableData() throws IOException {
        List<Object[]> data = new ArrayList<>();
        String line;
        while (!(line = in.readLine()).equals("END")) {
            data.add(line.split("\\|"));
        }
        return data;
    }

    /**
     * 교수클라이언트가 시작하는 기능입니다.
     *
     * @param args
     */
    public static void main(String[] args) {
        new professorClient();
    }
}
