/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.User;

import Model.InquiryExcel;
import java.util.Arrays;

/**
 *
 * @author leeseungmin
 */
public class UserMessageRouter {

    private InquiryExcel inquiryExcel;

    public UserMessageRouter() {
        this.inquiryExcel = new InquiryExcel();
    }

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
