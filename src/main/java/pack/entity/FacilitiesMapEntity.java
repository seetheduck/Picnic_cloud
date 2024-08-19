package pack.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public List<FacilitiesMapDto> toDto(List<FacilitiesMapEntity> entities) {
        return entities.stream()
            .map(entity -> FacilitiesMapDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .category(entity.getCategory())
                .address(entity.getAddress())
                .description(entity.getDescription())
                .homepageUrl(entity.getHomepageUrl())
                .instagramUrl(entity.getInstagramUrl())
                .holidayInfo(entity.getHolidayInfo())
                .ageLimit(entity.getAgeLimit())
                .operationTime(entity.getOperationTime())
                .kidsZone(entity.getKidsZone())
                .freeParking(entity.getFreeParking())
                .chargedParking(entity.getChargedParking())
                .telephone(entity.getTelephone())
                .nursingRoom(entity.getNursingRoom())
                .strollerRental(entity.getStrollerRental())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build())
            .collect(Collectors.toList());
    }

}



