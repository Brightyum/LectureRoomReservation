/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.admin;

import Model.Excel;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * UserManagement 클래스는 관리자가 사용자 정보를 관리하는 기능을 제공.
 * 사용자 정보는 Excel 파일로부터 읽고, 수정 및 삭제를 할 수 있으며,
 * 경고 횟수를 관리하거나 사용자 목록 조회, 사용자 추가 등의 기능을 포함.
 * 
 * @author user
 */
public class UserManagement implements Admin {

    private Excel excel;                        // 사용자 정보를 저장 및 불러오는 Excel 객체
    private List<List<String>> userInfo;        // 사용자 정보 전체를 저장하는 리스트 (각 사용자 정보는 List<String> 형태)
    private int userNameIdx;                    // 사용자 이름 열의 인덱스
    private Scanner sc;                         // 사용자 입력용 Scanner (테스트용)
    private int userNoticeIdx;                  // 사용자 경고 횟수 열의 인덱스
    private int userIdIdx;                      // 사용자 ID 열의 인덱스
    private int userPwdIdx;                     // 사용자 비밀번호 열의 인덱스
    private int userMajorIdx;                   // 사용자 전공 열의 인덱스

    /**
     * 기본 생성자: Excel 객체를 생성하고 Scanner(System.in) 사용
     */
    public UserManagement() {
        this(createDefaultExcel(), new Scanner(System.in));
    }

    /**
     * 주 생성자: Excel 객체와 Scanner 객체를 인자로 받아 초기화
     */
    public UserManagement(Excel excel, Scanner sc) {
        this.excel = excel;
        this.userNameIdx = 0;
        this.userNoticeIdx = 4;
        this.sc = sc;
        this.getUserInformation();             // Excel로부터 사용자 정보 읽기
        this.userIdIdx = 1;
        this.userPwdIdx = 2;
        this.userMajorIdx = 3;
    }

    /**
     * Excel 객체 생성 메서드 (예외 발생 시 null 반환)
     */
    private static Excel createDefaultExcel() {
        try {
            return new Excel();
        } catch (IOException ex) {
            System.out.println("Excel 생성 실패" + ex);
            return null;
        }
    }

    /**
     * 사용자 정보를 Excel로부터 읽어 userInfo 리스트에 저장
     */
    public void getUserInformation() {
        this.excel.readUser();
        userInfo = this.excel.getUserInfo();
        System.out.println(userInfo);         // 읽은 사용자 정보 출력 (디버깅용)
    }

    /**
     * 사용자 정보를 업데이트하는 메서드
     * @param list 새로운 사용자 정보 (이름, 비밀번호, 전공)
     * @param id 사용자 ID (수정 대상 식별)
     * @return 저장 성공 여부 반환
     */
    public boolean setUserInformation(List<String> list, String id) {
        for (List<String> user : this.userInfo) {
            if (id.equals(user.get(this.userIdIdx))) {
                int listNameIdx = 0;
                int listPwdIdx = 1;
                int listMajorIdx = 2;
                user.set(this.userNameIdx, list.get(listNameIdx));    // 이름 수정
                user.set(this.userPwdIdx, list.get(listPwdIdx));      // 비밀번호 수정
                user.set(this.userMajorIdx, list.get(listMajorIdx));  // 전공 수정
                System.out.println(user);                            // 수정된 사용자 정보 출력
            }
        }
        // 수정 후 Excel 저장
        try {
            this.excel.saveUserInfo(this.userInfo);
            System.out.println("저장 완료");
            return true;
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            return false;
        }
    }

    /**
     * 문제(문의사항) 내용을 출력하는 메서드
     * 경고 체크가 0인 사용자만 출력
     */
    @Override
    public void getUserContact() {
        int problemIdx = 5;              // 문의사항 내용 열 인덱스
        int problemCheckIdx = 6;         // 문의사항 처리 상태 열 인덱스
        String check = "0";              // 0 = 미처리 상태

        for (List<String> user : this.userInfo) {
            if (check.equals(user.get(problemCheckIdx))) {
                System.out.println(user.get(problemIdx));
            }
        }
    }

    /**
     * Admin 인터페이스의 checkAnother() 구현 (현재 미구현)
     */
    @Override
    public void checkAnother() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     * 모든 사용자 이름과 ID를 리스트로 반환
     * @return 사용자 이름 리스트
     */
    @Override
    public List<String> getUsers() {
        List<String> userNameList = new ArrayList<>();
        for (List<String> user : this.userInfo) {
            String userName = user.get(this.userNameIdx);
            String userId = user.get(this.userIdIdx);
            System.out.println("사용자 이름: " + userName + ", 사용자 ID: " + userId);
            userNameList.add(userName);
        }
        return userNameList;
    }

    /**
     * 모든 사용자 ID를 리스트로 반환
     * @return 사용자 ID 리스트
     */
    public List<String> getId() {
        List<String> userIdList = new ArrayList<>();
        for (List<String> user : this.userInfo) {
            String userId = user.get(this.userIdIdx);
            System.out.println("사용자 ID: " + userId);
            userIdList.add(userId);
        }
        return userIdList;
    }

    /**
     * 특정 ID가 존재하는지 확인
     * @param id 확인할 사용자 ID
     * @return 존재 여부
     */
    public boolean getIdCheck(String id) {
        List<String> userIdList = this.getId();
        for (String userId : userIdList) {
            if (id.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 사용자에게 경고를 부여 (경고 횟수 +1)
     * @param id 경고를 부여할 사용자 ID
     * @return 저장 성공 여부
     */
    public boolean setUserWarning(String id) {
        for (List<String> userList : this.userInfo) {
            if (id.equals(userList.get(userIdIdx))) {
                String userNoticeNum = userList.get(this.userNoticeIdx);
                int num = Integer.parseInt(userNoticeNum);
                num = num + 1;
                userList.set(this.userNoticeIdx, Integer.toString(num));
            }
        }
        System.out.println(this.userInfo);  // 경고 후 사용자 정보 출력
        try {
            this.excel.saveUserInfo(this.userInfo);
            System.out.println("저장 완료");
            return true;
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            return false;
        }
    }

    /**
     * 특정 사용자의 상세 정보를 반환
     * @param userId 조회할 사용자 ID
     * @return 사용자 정보 리스트 (ID, 이름, 비밀번호, 전공 등)
     */
    public List<String> getUserDetail(String userId) {
        for (List<String> userList : this.userInfo) {
            if (userId.equals(userList.get(userIdIdx))) {
                return userList;
            }
        }
        return null;
    }

    /**
     * 새로운 사용자 추가
     * @param newUser 새 사용자 정보 리스트
     * @return 성공 여부
     */
    public boolean addUser(List<String> newUser) {
        String newId = newUser.get(this.userIdIdx);
        if (this.getIdCheck(newId)) {
            System.out.println("이미 존재하는 아이디입니다.");
            return false;
        }
        this.userInfo.add(newUser);
        try {
            this.excel.saveUserInfo(this.userInfo);
            System.out.println("저장 완료");
            return true;
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            return false;
        }
    }

    /**
     * 사용자 삭제 (ID로 삭제)
     * @param id 삭제할 사용자 ID
     * @return 삭제 성공 여부
     */
    public boolean removeUser(String id) {
        Iterator<List<String>> iterator = this.userInfo.iterator();
        while (iterator.hasNext()) {
            List<String> user = iterator.next();
            if (id.equals(user.get(this.userIdIdx))) {
                iterator.remove();
                this.excel.saveUserInfo(this.userInfo);
                return true;
            }
        }
        return false;
    }

    /**
     * main 메서드 (테스트용)
     */
    public static void main(String args[]) throws IOException {
        UserManagement admin = new UserManagement();
        admin.getUserContact();  // 미처리 문의 출력
    }

    /**
     * Admin 인터페이스의 setUserInformation() 구현 (현재 미구현)
     */
    @Override
    public void setUserInformation() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
