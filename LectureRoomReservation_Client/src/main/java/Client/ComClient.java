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
        socket = new Socket("localhost", 10020); // 서버 주소 및 포트
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * 고장난 컴퓨터 목록 요청
     * @return 서버의 응답 문자열
     */
    public String requestBrokenComputerList() {
        try {
            out.println("GET_BROKEN_LIST");
            return in.readLine();
        } catch (IOException e) {
            return "ERROR|서버 응답 실패";
        }
    }

    public void close() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}