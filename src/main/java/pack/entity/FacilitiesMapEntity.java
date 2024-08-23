package pack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.FacilitiesMapDto;

@Entity
@Table(name = "facilities_map")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilitiesMapEntity {

    @Id
    private Integer id;

    private String name;
    private Float latitude;
    private Float longitude;
    private String category;
    private String address;
    private String description;
    private String homepageUrl;
    private String instagramUrl;
    private String holidayInfo;
    private String ageLimit;
    private String operationTime;
    private Boolean kidsZone;
    private Boolean freeParking;
    private Boolean chargedParking;
    private String telephone;
    private Boolean nursingRoom;
    private Boolean strollerRental;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 개별 엔티티를 DTO로 변환하는 메서드
    public FacilitiesMapDto toDto() {
        return FacilitiesMapDto.builder()
                .id(this.id)
                .name(this.name)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .category(this.category)
                .address(this.address)
                .description(this.description)
                .homepageUrl(this.homepageUrl)
                .instagramUrl(this.instagramUrl)
                .holidayInfo(this.holidayInfo)
                .ageLimit(this.ageLimit)
                .operationTime(this.operationTime)
                .kidsZone(this.kidsZone)
                .freeParking(this.freeParking)
                .chargedParking(this.chargedParking)
                .telephone(this.telephone)
                .nursingRoom(this.nursingRoom)
                .strollerRental(this.strollerRental)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
