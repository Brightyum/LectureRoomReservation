/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.User;

import Model.InquiryExcel;
import java.util.Arrays;

/**
 * 사용자 관련 서버 명령을 처리하는 라우터 클래스입니다.
 *
 * @author leeseungmin
 */
public class UserMessageRouter {

    private InquiryExcel inquiryExcel;  // 문의정보를 엑셀 파일로 관리하는 객체

    public UserMessageRouter() {
        this.inquiryExcel = new InquiryExcel();
    }
    
    /**
     * 사용자 클라이언트로 받은 input을 해석해서 해당 명령에 맞는 동작을 수행 , 결과를 out(스트림) 으로 전송합니다
     * 
     * @param input 클라이언트로 부터 받은 문자열
     * @return input 처리 결과 SUCCESS , FAIL or UNKNOWN_COMMAND
     */
    public String judgeCommand(String input) {
        if (input.startsWith("CREATE_INQUIRY|")) {
            String[] parts = input.split("\\|", 6);
            if (parts.length != 6) {
                System.out.println("[서버] CREATE_INQUIRY 필드 개수 오류: " + input);
                return "FAIL";
            }
            System.out.println("[서버] CREATE_INQUIRY 파싱 값: " + Arrays.toString(parts));
            String name = parts[1];
            String id = parts[2];
            String message = parts[3];
            String time = parts[4];
            boolean isPriority = Boolean.parseBoolean(parts[5]);

            System.out.println("[서버] 문의 등록 시도: " + name + ", " + id + ", " + message + ", " + time + ", " + isPriority);

            boolean success = inquiryExcel.addInquiry(name, id, message, time, isPriority);
            System.out.println("[서버] 문의 등록 결과: " + (success ? "SUCCESS" : "FAIL"));
            return success ? "SUCCESS" : "FAIL";
        }
        return "UNKNOWN_COMMAND";
    }
}
