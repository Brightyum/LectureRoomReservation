/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import db.BrokenComputer;
import db.RoomStatus;

/**
 *
 * @author user
 */
public class RoomInfo implements System {
    
    private BrokenComputer brokenComputer;
    private RoomStatus roomStatus;
    
    public RoomInfo() {
        try {
            this.brokenComputer = new BrokenComputer();
            this.roomStatus = new RoomStatus();
        } catch (IOException ex) {
            Logger.getLogger(RoomInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void getLectureRoom() {
        Scanner scanner = new Scanner(java.lang.System.in);
        java.lang.System.out.print("강의실 번호를 입력하세요: ");
        String roomID = scanner.nextLine().trim();
        roomStatus.printRoomStatus(roomID);
    }

    @Override
    public void getComAction() {
        try {
            brokenComputer.readAndPrintBrokenComputer();
        } catch (Exception ex) {
            Logger.getLogger(RoomInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void reserveLectureSeat() {
        Scanner scanner = new Scanner(java.lang.System.in);
        
        java.lang.System.out.print("강의실 번호: ");
        String roomID = scanner.nextLine();
        
        java.lang.System.out.print("좌석 번호: ");
        String seatNumStr = scanner.nextLine();
        
        java.lang.System.out.print("사용자 이름: ");
        String userName = scanner.nextLine();
        
        java.lang.System.out.print("사용자 ID: ");
        String userID = scanner.nextLine();
        
        java.lang.System.out.print("예약 날짜 (YYYY.MM.DD): ");
        String date = scanner.nextLine();
        
        java.lang.System.out.print("예약 시간 (HH:MM): ");
        String time = scanner.nextLine();
        
        try {
            int seatNum = Integer.parseInt(seatNumStr.trim());
            roomStatus.reserveSeat(seatNum, roomID, userName, userID, date, time);
        } catch (NumberFormatException e) {
            java.lang.System.out.println("좌석 번호는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            Logger.getLogger(RoomInfo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void main(String[] args) {
        RoomInfo roomInfo = new RoomInfo();
        roomInfo.getComAction();
        roomInfo.getLectureRoom();
        roomInfo.reserveLectureSeat();
    }
    
}
