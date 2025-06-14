/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author leeseungmin
 */
public class Inquiry {

    private String name;                // 문의자 이름
    private String id;                  // 문의자 id
    private String message;             // 문의내용
    private String answeredInquiries;   // 답변
    private LocalDateTime time;         // 문의한 시간
    private boolean isChecked;          // 문의내용 확인여부
    private boolean isPriority;         // 중요도
    
    public Inquiry() {
        this.id = LoginInfo.getUserId();
        this.name = LoginInfo.getUserName();
    }

    public Inquiry(String name, String id, String message, LocalDateTime time, boolean ischecked, String answeredInquiries, boolean isPriority) {
        this.name = name;
        this.id = id;
        this.message = message;
        this.time = time;
        this.isChecked = ischecked;
        this.answeredInquiries = answeredInquiries;
        this.isPriority = isPriority;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getAnsweredInquiries() {
        return answeredInquiries;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean Priority) {
        this.isPriority = Priority;
    }
    
    /**
     * inquiry 객체의 정보를 문자열로 반환하는 기능입니다.
     * 
     * @return 객체 정보를 '|' 로 구분해서 문자열로 반환합니다.
     */
    public String toNetworkString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                name,
                id,
                message,
                time.toString(),
                isChecked,
                answeredInquiries,
                isPriority
        );
    }

    /**
     * 네트워크로 전송된 문자열을 다시 inquiry 객체로 역변환하는 기능입니다.
     * 
     * @param data 네트워크로 받은 inquiry 정보 문자열입니다.
     * @return 파싱에 성공하면 inquiry객체 실패하면 null을 반환합니다.
     */
    public static Inquiry fromNetworkString(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        String[] parts = data.split("\\|", -1);
        if (parts.length != 7) {
            System.err.println("잘못된 데이터 포맷: " + data);
            return null;
        }
        try {
            return new Inquiry(
                    parts[0],
                    parts[1],
                    parts[2],
                    LocalDateTime.parse(parts[3]),
                    Boolean.parseBoolean(parts[4]),
                    parts[5],
                    Boolean.parseBoolean(parts[6])
            );
        } catch (Exception e) {
            System.err.println("파싱 실패: " + data);
            return null;
        }
    }
}
