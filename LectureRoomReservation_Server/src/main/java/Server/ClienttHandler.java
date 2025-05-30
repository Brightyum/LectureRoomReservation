/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Server.Login.LoginMessageRouter;
import Server.Admin.AdminMessageRouter;
import Server.User.UserMessageRouter;
import Server.Professor.ProfessorMessageRouter;
import Server.System.ComMessageRouter;
import Server.System.ReservationListMessageRouter;
import Model.BrokenComputer;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author duatm
 */
public class ClienttHandler implements Runnable {

    private Socket clientSocket;
    private static final int MAX_USER = 4;
    private static final AtomicInteger userCount = new AtomicInteger(0);
    private AdminMessageRouter router;
    private ProfessorMessageRouter professorRouter;
    private UserMessageRouter userRouter;

    public ClienttHandler(Socket socket) {
        this.clientSocket = socket;
        this.router = new AdminMessageRouter();
    }

    @Override
    public void run() {
        if (userCount.incrementAndGet() > MAX_USER) {
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
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            out.println("서버에 접속하셨습니다.");

            String roleLine = in.readLine();
            if (roleLine == null) {
                out.println("INVALID_ROLE");
                return;
            }
            System.out.println(roleLine);
            if (roleLine.startsWith("login")) {
                LoginMessageRouter loginRouter = new LoginMessageRouter();
                System.out.println("로그인 라우터");
                String input;
                
                while ((input = in.readLine()) != null) {
                    if (handleSystemCommand(input, out)) continue;
                    String response = loginRouter.judgeCommand(input);
                    if (response != null) {
                        out.println(response);
                    }
                }
            }
            
            else if (roleLine.startsWith("ROLE=PROFESSOR")) {
                ProfessorMessageRouter professorRouter = new ProfessorMessageRouter();
                System.out.println("교수 라우터");
                String input;
                while ((input = in.readLine()) != null) {
                    if (handleSystemCommand(input, out)) continue;
                    String response = professorRouter.judgeCommand(input, out);
                    if (response != null) {
                        out.println(response);
                    }
                }
            } else if (roleLine.startsWith("ROLE=ADMIN")) {
                AdminMessageRouter adminRouter = new AdminMessageRouter();
                System.out.println("관리자 라우터");
                String input;
                while ((input = in.readLine()) != null) {
                    if (handleSystemCommand(input, out)) continue;
                    String response = adminRouter.judgeCommand(input);
                    if (response != null) {
                        out.println(response);
                    }
                }
            } else if (roleLine.startsWith("ROLE=USER")) {
                UserMessageRouter userRouter = new UserMessageRouter();
                System.out.println("사용자 라우터");
                String input;
                while ((input = in.readLine()) != null) {
                    if (handleSystemCommand(input, out)) continue;
                    String response = userRouter.judgeCommand(input,out);
                    if (response != null) {
                        out.println(response);
                    }
                }
            } else {
                out.println("INVALID_ROLE");
                return;
            }

        } catch (IOException e) {
            System.out.println("사용자 처리 중 오류: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("사용자 연결 종료");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                userCount.decrementAndGet();
                System.out.println("현재 접속자 수: " + userCount.get());
            }
        }
    }
    private boolean handleSystemCommand(String input, PrintWriter out) {
        System.out.println("[handleSystemCommand] input: " + input);

        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        try {
            if (input.startsWith("GET_BROKEN_LIST")) {
                BrokenComputer brokenComputer = new BrokenComputer();
                ComMessageRouter comRouter = new ComMessageRouter(brokenComputer);
                String response = comRouter.handleMessage(input);
                System.out.println("[GET_BROKEN_LIST] response: " + response);
                out.println(response);
                return true;
            } else if (input.startsWith("GET_RESERVATION_LIST")) {
            // 메시지 포맷 점검
                String[] parts = input.split("\\|");
                if (parts.length < 3) {
                    out.println("ERROR|Invalid command format for GET_RESERVATION_LIST");
                    System.out.println("[GET_RESERVATION_LIST] invalid format: " + input);
                    return true;
                }

                ReservationListMessageRouter listRouter = new ReservationListMessageRouter();
                String response = listRouter.routeMessage(input);
                System.out.println("[GET_RESERVATION_LIST] response: " + response);
                out.println(response);
                return true;
            }
        } catch (IOException e) {
        e.printStackTrace();
        out.println("ERROR|Internal server error");
        return true;
        } catch (Exception e) {
        e.printStackTrace();
        out.println("ERROR|Unexpected server error");
        return true;
        }

        return false;
    }
}
