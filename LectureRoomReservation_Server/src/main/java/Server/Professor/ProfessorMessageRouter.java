/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Professor;

import Model.RoomStatus;
import java.io.PrintWriter;
import java.util.Arrays;
import Model.Inquiry;
import Model.InquiryExcel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author leeseungmin
 */
public class ProfessorMessageRouter {

    private final RoomStatus roomStatus;
    private InquiryExcel inquiryExcel;

    public ProfessorMessageRouter() throws IOException {
        this.inquiryExcel = new InquiryExcel();
        this.roomStatus = new RoomStatus();
    }

    public String judgeCommand(String input, PrintWriter out) throws IOException {
        if ("GET_INQUIRY_LIST".equals(input.trim())) {
            List<Inquiry> inquiries = inquiryExcel.loadAllInquiries();
            for (Inquiry inquiry : inquiries) {
                out.println(inquiry.toNetworkString());
            }
            out.println("END");
            return null;
        }

        if (input.startsWith("GET_ROOM_LIST")) {
            try {
                XSSFWorkbook workbook = roomStatus.getWorkbook();
                List<String> rooms = new ArrayList<>();
                for (int i = 2; i <= 8; i++) {
                    String sheetName = workbook.getSheetName(i);
                    rooms.add(sheetName);
                }
                for (String room : rooms) {
                    out.println(room);
                }
                out.println("END");
                return null;
            } catch (Exception e) {
                out.println("FAIL|SERVER_ERROR");
                return "FAIL|SERVER_ERROR";
            }
        }

        if (input.startsWith("GET_PAST_RESERVATIONS|")) {
            String[] parts = input.split("\\|");
            if (parts.length != 2) {
                out.println("FAIL|INVALID_FORMAT");
                return "FAIL|INVALID_FORMAT";
            }
            String roomID = parts[1];
            try {
                RoomStatus roomStatus = new RoomStatus();
                List<String> reservations = roomStatus.getReservationsByRoom(roomID);
                for (String res : reservations) {
                    out.println(res); 
                }
                out.println("END"); 
                return null;
            } catch (Exception e) {
                out.println("FAIL|SERVER_ERROR");
                return "FAIL|SERVER_ERROR";
            }
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
