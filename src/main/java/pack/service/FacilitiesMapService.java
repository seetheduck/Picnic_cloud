package pack.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.FacilitiesMapDto;
import pack.entity.FacilitiesMapEntity;
import pack.repository.FacilitiesMapRepository;

import java.util.List;

@Service
public class FacilitiesMapService {

    private final FacilitiesMapRepository facilitiesMapRepository;

    public FacilitiesMapService(FacilitiesMapRepository facilitiesMapRepository) {
        this.facilitiesMapRepository = facilitiesMapRepository;
    }

    @Transactional(readOnly = true)
    public List<FacilitiesMapDto> getAllFacilities() {
        List<FacilitiesMapEntity> entities = facilitiesMapRepository.findAll();
        return new FacilitiesMapEntity().toDto(entities);
    }
}
