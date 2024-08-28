package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.entity.UserEntity;
import pack.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    // id를 기반으로 UserEntity를 조회하여 반환하는 메서드
    public UserEntity findById(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        
        if (userEntity != null) {
            return userEntity;
        } else {
            // 필요한 경우, 사용자를 찾지 못했을 때의 처리
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
