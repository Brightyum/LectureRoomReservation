/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller.system;

/**
 *
 * @author user
 */
public interface System {
    // 강의실 현황
    void getLectureRoom();
    
    //컴퓨터 작동 현황
    void getComAction();
    
    // 강의실 자리 예약
    void reserveLectureSeat();
}
