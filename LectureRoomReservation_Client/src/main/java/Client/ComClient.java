/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author user
 */

/**
 * ComMessageRouter와 통신하는 클라이언트
 */
public class ComClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ComClient() throws IOException {
        socket = new Socket("localhost", 10020);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 역할 알리기
        out.println("ROLE=ADMIN");
        String serverMessage = in.readLine();
        System.out.println("[ComMessageClient] 서버: " + serverMessage);
    }

    /**
     * 고장 컴퓨터 목록 요청 메서드
     * 메시지: BROKEN|LIST
     * @return 서버 응답 문자열 (고장 컴퓨터 목록)
     */
    public String requestBrokenComputerList() {
        String msg = "GET_BROKEN_LIST";
        out.println(msg);
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR|서버 응답 실패";
        }
    }

    public void close() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            System.out.println("[ComMessageClient] 연결 종료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        public static void main(String[] args) throws IOException {
        new ComClient();
    }
}