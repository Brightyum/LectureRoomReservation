/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.System;

import Model.RoomStatus;
import java.io.IOException;

/**
 *
 * @author user
 */
public class ReservationListMessageRouter {
    private RoomStatus roomStatus;

    public ReservationListMessageRouter() throws IOException {
        roomStatus = new RoomStatus();
    }

    public String routeMessage(String message) {
        String[] parts = message.split("\\|");
        if (parts.length < 3 || !parts[0].equals("GET_RESERVATION_LIST")) {
            return "ERROR|Invalid command";
        }

        String roomId = parts[1];
        String date = parts[2];

        // 예약 목록 받아오기
        String reservations = roomStatus.getReservationList(roomId, date);
        if (reservations.isEmpty()) {
            return "EMPTY|No reservations";
        }

        // 줄 단위로 표시
        return "SUCCESS|" + String.join("\n", reservations);
    }
}
