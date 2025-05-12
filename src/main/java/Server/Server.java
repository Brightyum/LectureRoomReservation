/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duatm
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ClientHandler handler;
    
    public Server() {
        try {
            this.serverSocket = new ServerSocket(10020);
            System.out.println("현재 서버 대기중입니다.");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("서버의 연결을 다시 점검하세요.(포트)");
        }
        
    }
    
    public void run() {
        while(true) {
            try {
                this.clientSocket = serverSocket.accept();
                System.out.println("사용자가 연결되었습니다.");

                handler = new ClientHandler(this.clientSocket);
                new Thread(handler).start();

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("사용자의 연결을 다시 점검하세요.");
            }
        }
    }
}
