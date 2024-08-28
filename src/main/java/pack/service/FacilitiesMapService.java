package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.FacilitiesMapDto;
import pack.entity.FacilitiesMapEntity;
import pack.repository.FacilitiesMapRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilitiesMapService {
	@Autowired
    private FacilitiesMapRepository facilitiesMapRepository;

    @Transactional(readOnly = true)
    public List<FacilitiesMapDto> getAllFacilities() {
        List<FacilitiesMapEntity> entities = facilitiesMapRepository.findAll();
        // 각 엔티티를 DTO로 변환
        return entities.stream()
                       .map(FacilitiesMapEntity::toDto)
                       .collect(Collectors.toList());
    }
}
