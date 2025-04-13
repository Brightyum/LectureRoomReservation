/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author user
 */
public interface Login {
    
    // 회원가입 메서드
    void signUp();
    
    // 회원정보 입력 
    void setInformation();
    
    // 회원정보 반환
    void getInformation();
    
    // 로그인 성공 메서드
    static void successLogin() {
        System.out.println("로그인에 성공하였습니다");
    }
    
    // 로그인 실패 메서드
    static void failLogin() {
        System.out.println("로그인에 성공하지 못하였습니다.");
    }
}
