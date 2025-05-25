/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Professor;

import java.io.PrintWriter;
import java.util.Arrays;
import Model.Inquiry;
import Model.InquiryExcel;
import java.util.List;

/**
 *
 * @author leeseungmin
 */
public class ProfessorMessageRouter {

    private InquiryExcel inquiryExcel;

    public ProfessorMessageRouter() {
        this.inquiryExcel = new InquiryExcel();
    }

    public String judgeCommand(String input, PrintWriter out) {
        if ("GET_INQUIRY_LIST".equals(input.trim())) {
            List<Inquiry> inquiries = inquiryExcel.loadAllInquiries();
            for (Inquiry inquiry : inquiries) {
                out.println(inquiry.toNetworkString());
            }
            out.println("END");
            return null; 
        }

        if (input.startsWith("UPDATE_ANSWER|")) {
            String[] parts = input.split("\\|", 5);
            if (parts.length != 5) {
                System.out.println("[서버] 필드 개수 오류: " + Arrays.toString(parts));
                return "FAIL";
            }
            String name = parts[1];
            String id = parts[2];
            String time = parts[3];
            String answer = parts[4];

            boolean success = inquiryExcel.updateAnswer(name, id, time, answer);
            String result = success ? "SUCCESS" : "FAIL";
            out.println(result);
            out.flush();
            return result;
        }

        return "UNKNOWN_COMMAND";
    }

}
