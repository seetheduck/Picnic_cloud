package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.FacilitiesMapDto;
import pack.service.FacilitiesMapService;

@RestController
public class FacilitiesMapController {
	@Autowired
    private FacilitiesMapService facilitiesMapService;

    // API 데이터를 반환하는 메서드
    @GetMapping("/facilities-map")
    public List<FacilitiesMapDto> getAllFacilities() {
        return facilitiesMapService.getAllFacilities();
    }
}
