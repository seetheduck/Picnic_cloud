package pack.dto;

import lombok.*;
import pack.entity.FacilitiesMapEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilitiesMapDto {

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

    // Converts Dto to Entity
    public FacilitiesMapEntity toEntity() {
        return FacilitiesMapEntity.builder()
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
