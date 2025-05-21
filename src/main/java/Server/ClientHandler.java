/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author duatm
 */
public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private static final int MAX_USER = 3;
    private static final AtomicInteger userCount = new AtomicInteger(0);
    
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    
    @Override
    public void run() {
        if (userCount.incrementAndGet() >  MAX_USER) {
            userCount.decrementAndGet();
            try {
                PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
                out.println("서버에 이용자가 가득 찼습니다. 나중에 다시 시도하세요");
                
                this.clientSocket.close();
            } catch (IOException e) {
                System.out.println("연결중 오류 발생: " + e.getMessage());
            }
            return;
        }
       
        System.out.println("현재 접속자 수: " + userCount.get());
        
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        ) {
            out.println("서버에 접속하셨습니다.");
            
            String input;
            while((input = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(input.trim())) {
                    break;
                }
                System.out.println("[" + this.clientSocket.getInetAddress() + "]" + input);
                out.println("서버 응답: " + input);
            }
        } catch (IOException e) {
            System.out.println("사용자 처리 중 오류 발생 " + e.getMessage());
        } finally {
            try {
                this.clientSocket.close();
                System.out.println("사용자 연결 종료");
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                userCount.decrementAndGet();
                System.out.println("현재 접속자 수: " + userCount.get());
            }
        }
    }
}
