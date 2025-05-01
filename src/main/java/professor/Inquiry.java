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
    private String id;// 문의자 id
    private String name;// 문의자 이름
    private String message;// 문의내용
    private LocalDateTime time;// 문의한 시간
    private boolean checked;// 문의내용 확인여부

    public Inquiry(String id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.time = LocalDateTime.now();
        this.checked = false;
    }

    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getMessage() { return message; }
    public LocalDateTime getTime() { return time; }
    
    public boolean isChecked() {// boolean 으로 처리 or 미처리 된문의사항을 가른다
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    @Override
    public String toString() {
        return "[" + time + "] " + name + "(" + id + "): " + message;
    }
}