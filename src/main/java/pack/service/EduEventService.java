package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.entity.EduEventEntitiy;
import pack.repository.EduEventRepository;

import java.util.List;

@Service
public class EduEventService {
    @Autowired
    private EduEventRepository eduEventRepository;

    // 저장된 모든 이벤트 데이터를 가져오는 메서드
    public List<EduEventEntitiy> getAllEduEvents() {
        return eduEventRepository.findAll(); // 데이터베이스에서 모든 데이터를 조회
    }

}
