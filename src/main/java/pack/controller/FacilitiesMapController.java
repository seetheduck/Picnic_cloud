package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.dto.FacilitiesMapDto;
import pack.service.FacilitiesMapService;

@Controller
public class FacilitiesMapController {
	@Autowired
    private FacilitiesMapService facilitiesMapService;

    public FacilitiesMapController(FacilitiesMapService facilitiesMapService) {
        this.facilitiesMapService = facilitiesMapService;
    }

    // 뷰를 반환하는 메서드
    @GetMapping("/map")
    public String toMap() {
        return "map/mapIndex";
    }

    // API 데이터를 반환하는 메서드
    @GetMapping("/api/facilities")
    @ResponseBody
    public List<FacilitiesMapDto> getAllFacilities() {
        return facilitiesMapService.getAllFacilities();
    }
}
