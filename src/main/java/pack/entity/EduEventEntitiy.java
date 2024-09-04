package pack.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "eduevent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EduEventEntitiy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;  // 자동 생성

    private String title;
    private String startDate;
    private String endDate;
    private String link;
    private String imageUrl;
    private String location;
    private String day;

    // 빌더에서 자동 생성되는 no 필드를 제외할 수 있도록 설정
    @Builder
    public EduEventEntitiy(String title, String startDate, String endDate, String link, String imageUrl, String location, String day) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
        this.imageUrl = imageUrl;
        this.location = location;
        this.day = day;
    }
}
