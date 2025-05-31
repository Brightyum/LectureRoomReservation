/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Client.LoginServerResponse.LoginServerResponse;
import View.ClientFullView;
import View.Login.LoginView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author user
 */
public class Login {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private LoginView view;
    private LoginServerResponse lsr;
    public Login() {
        try {
            this.socket = new Socket("localhost", 10020);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            String serverMessage = in.readLine();
            
            if (serverMessage != null && serverMessage.contains("접속하셨습니다.")) {
                out.println("login");
                this.view = new LoginView(this);
                this.listenFromServer();
                this.lsr= new LoginServerResponse(view);

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
                    lsr.judgeCommand(response);
                }
            } catch (IOException e) {
                System.out.println("서버 응답 수신 중 오류");
            }
        }).start();
    }
    
    public void closeSilently() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();   
        } catch (IOException e) {
            
        }
    }
    
    public LoginServerResponse getServerResponse() {
        return this.lsr;
    }
}
