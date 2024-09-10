package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pack.entity.EduEventEntitiy;
import pack.service.EduEventService;

import java.util.List;

@RestController
@RequestMapping("/edu-events")
public class EduEventController {
    @Autowired
    private EduEventService eduEventService;

    // 모든 교육 이벤트 데이터를 반환하는 메서드
    @GetMapping
    public List<EduEventEntitiy> getAllEduEvents() {
        return eduEventService.getAllEduEvents(); // 서비스 호출 후 결과 반환
    }

}