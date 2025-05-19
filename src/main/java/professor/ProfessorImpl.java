/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package professor;


import db.InquiryExcel;


/**
 *
 * @author leeseungmin
 */

public class ProfessorImpl implements Professor {
    //테스트용 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    Inquiry inquiry = new Inquiry();
    
    private InquiryExcel inquiryExcel = new InquiryExcel();

    @Override
    public void getRoster() {
        // TODO: 현제강의실 사용 명단 확인 구현 예정
    }

    @Override
    public void getPastRoster() {
        // TODO: 강의실 과거 사용 명단 확인 구현 예정
    }
    
    @Override
    public void CreateInquiry() {   //TestId 지워야함 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        new CreateInquiryFrame(inquiry.TestId,inquiry.TestName).setVisible(true);
    }

    @Override
    public void PshowInquiry() {
        new PshowInquiryFrame().setVisible(true);
    }

    @Override
    public void UshowInquiry() {       //TestId 지워야함 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        new UshowInquiryFrame(inquiry.TestId,inquiry.TestName).setVisible(true);
    }

    @Override
    public void AnsweredInquiries() {
        new PshowInquiryFrame().setVisible(true);
    }
}