/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import java.io.*;
import java.net.*;

/**
 *
 * @author duatm
 */
public class Client {
    private Socket socket;
    private AdminView view;
    private PrintWriter out;
    private BufferedReader in;
    
    public Client() {
        try {
            this.socket = new Socket("localhost", 10020);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            
            String serverMessage = in.readLine();
            
            if (serverMessage == null) {
                System.out.println("서버로부터 응답이 없습니다.");
                return;
            }
            
            if (serverMessage.contains("접속하셨습니다.")) {
                this.view = new AdminView(serverMessage, this);
                view.show();
            }  else if (serverMessage.contains("가득")) {
                new ClientFullView();
                this.closeSilently();
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    public static void main(String[] args) {
        new Client();
    }
}
