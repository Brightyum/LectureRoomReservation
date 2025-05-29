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
import Model.RoomReservation;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 교수 관련 서버 명령을 처리하는 라우터 클래스입니다.
 * 
 * @author leeseungmin
 */
public class ProfessorMessageRouter {
    private final RoomStatus roomStatus;    // 강의실 상태 및 예약 정보를 관리하는 객체
    private InquiryExcel inquiryExcel;      // 문의 내역을 엑셀 파일로 관리하는 객체

    public ProfessorMessageRouter() throws IOException {
        this.inquiryExcel = new InquiryExcel();
        this.roomStatus = new RoomStatus();
    }
    
    /**
     * 교수 클라이언트로 받은 input을 해석해서 해당 명령에 맞는 동작을 수행 , 결과를 out(스트림) 으로 전송합니다
     * 
     * @param input 클라이언트로부터 받은 문자열
     * @param out   결과를 전송할 출력 스트림
     * @return      명령 처리 결과 문자열
     * @throws IOException 입출력 오류발생시
     */
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
        if (input.startsWith("TRY_RESERVATION|")) {
            String[] parts = input.split("\\|", 7);
            if (parts.length != 7) {
                out.println("TRY_RESERVATION_FAIL|INVALID_FORMAT");
                return "TRY_RESERVATION_FAIL|INVALID_FORMAT";
            }

            String room = parts[1];
            String seat = parts[2];
            String name = parts[3];
            String id = parts[4];

            // 날짜 포맷 변경
            String rawDate = parts[5];
            String date = LocalDate.parse(rawDate).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

            String time = parts[6];

            try {
                RoomReservation rr = new RoomReservation();
                boolean success = rr.addReservation(room, seat, name, id, date, time);
                if (success) {
                    out.println("TRY_RESERVATION_SUCCESS");
                    return null;
                } else {
                    out.println("TRY_RESERVATION_FAIL|중복 예약 또는 오류");
                    return "TRY_RESERVATION_FAIL|중복 예약 또는 오류";
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("TRY_RESERVATION_FAIL|서버 오류");
                return "TRY_RESERVATION_FAIL|서버 오류";
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
