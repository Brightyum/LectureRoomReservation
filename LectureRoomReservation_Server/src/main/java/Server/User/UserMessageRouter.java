/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.User;

import Model.Inquiry;
import Model.InquiryExcel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * 사용자 관련 서버 명령을 처리하는 라우터 클래스입니다.
 *
 * @author leeseungmin
 */
public class UserMessageRouter {

    private InquiryExcel inquiryExcel;

    public UserMessageRouter() throws IOException {
        this.inquiryExcel = new InquiryExcel();
    }
    
    /**
     * 사용자 클라이언트로 받은 input을 해석해서 해당 명령에 맞는 동작을 수행 , 결과를 out(스트림) 으로 전송합니다
     * 
     * @param input 클라이언트로부터 받은 명령 문자열
     * @param out 결과를 전송할 출력 스트림
     * @return 명령 처리 결과 문자열
     */
    public String judgeCommand(String input, PrintWriter out) {
        if (input.startsWith("GET_MY_INQUIRIES|")) {
            String[] parts = input.split("\\|");
            if (parts.length != 2) {
                out.println("FAIL|INVALID_FORMAT");
                return "FAIL|INVALID_FORMAT";
            }
            String userId = parts[1];
            List<Inquiry> userInquiries = inquiryExcel.getInquiriesByUserId(userId);
            for (Inquiry inquiry : userInquiries) {
                out.println(inquiry.toNetworkString());
            }
            out.println("END");
            return null;
        }
        if (input.startsWith("CREATE_INQUIRY|")) {
            String[] parts = input.split("\\|", 6);
            if (parts.length != 6) {
                out.println("FAIL|INVALID_FORMAT");
                return "FAIL|INVALID_FORMAT";
            }
            String name = parts[1];
            String id = parts[2];
            String message = parts[3];
            String time = parts[4];
            boolean isPriority = Boolean.parseBoolean(parts[5]);
            boolean success = inquiryExcel.addInquiry(name, id, message, time, isPriority);
            out.println(success ? "SUCCESS" : "FAIL");
            return null;
        }
        out.println("UNKNOWN_COMMAND");
        return "UNKNOWN_COMMAND";
    }
}
