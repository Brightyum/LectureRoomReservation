/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package professor;
import java.time.LocalDateTime;
/**
 *
 * @author leeseungmin
 */
public class Inquiry {
    private String name;// 문의자 이름
    private String id;// 문의자 id
    private String message;// 문의내용
    private String answeredInquiries;// 답변
    private LocalDateTime time;// 문의한 시간
    private boolean isChecked;// 문의내용 확인여부
    private boolean isPriority;// 중요도
    // 지워야함 //
    public String TestId;
    public String TestName;
    
    public Inquiry(){
        this.TestId = "id2";
        this.TestName = "유재석";
    }

    public Inquiry(String name, String id, String message, LocalDateTime time, boolean ischecked , String answeredInquiries , boolean isPriority ) {
        this.name = name;
        this.id = id;
        this.message = message;
        this.time = time;
        this.isChecked = ischecked;
        this.answeredInquiries = answeredInquiries;
        this.isPriority = isPriority;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getMessage() { return message; }
    public LocalDateTime getTime() { return time; }
    public String getAnsweredInquiries() {return answeredInquiries; }
    public boolean isChecked() { return isChecked; }
    public void setChecked(boolean checked) { this.isChecked = checked; }
    public boolean isPriority() {return isPriority; }
    public void setPriority(boolean Priority) {this.isPriority = Priority; }
}