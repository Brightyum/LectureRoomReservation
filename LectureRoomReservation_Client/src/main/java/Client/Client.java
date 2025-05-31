/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Client.AdminServerResponse.ServerResponse;
import View.ClientFullView;
import View.Admin.AdminView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author user
 */
public class Client {
    private Socket socket;
    private AdminView view;
    private PrintWriter out;
    private BufferedReader in;
    private ServerResponse sr;
    
    public Client() {
        try {
            this.socket = new Socket("localhost", 10020);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out.println("ROLE=ADMIN");
            String serverMessage = in.readLine();
            
            if (serverMessage != null && serverMessage.contains("접속하셨습니다.")) {
                //this.view = new AdminView(this);
                this.listenFromServer();
                this.sr= new ServerResponse();

            }  else if (serverMessage.contains("가득")) {
                new ClientFullView();
                //this.closeSilently();
            } else {
                System.out.println("서버로부터 응답이 없습니다.");
                System.out.println(serverMessage);
                return;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String msg) {
        this.out.println(msg);
    }
    
    public void listenFromServer() {
        new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println("서버로부터 응답: " + response);
                    sr.judgeCommand(response);
                }
            } catch (IOException e) {
                System.out.println("서버 응답 수신 중 오류");
            }
        }).start();
    }
    
    public void sendClose() {
        try {
            this.out.println("종료를 하겠습니다.");
            this.out.close();
            this.in.close();
            this.socket.close();
            System.out.println("사용자 종료 되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    
    public void closeSilently() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();   
        } catch (IOException e) {
            
        }
    }
    
    public ServerResponse getServerResponse() {
        return this.sr;
    }
    
    public static void main(String[] args) {
        new Client();
    }
}

