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
            
            String s = in.readLine();
            this.view = new AdminView(s, this);
            view.show();
            
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
    
    public static void main(String[] args) {
        new Client();
    }
}
